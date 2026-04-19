package com.hotguy.warehouse13.dataservice;

import com.hotguy.warehouse13.model.PerishableProduct;
import com.hotguy.warehouse13.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of {@link DataRepository}.
 * Singleton — obtain the instance via {@link #getInstance()}.
 */
public class DatabaseAccess implements DataRepository {

    private static final String TABLE_PRODUCTS   = "products";
    private static final String TABLE_PERISHABLE = "perishable_products";

    private static DatabaseAccess instance;

    private DatabaseAccess() {}

    public static synchronized DatabaseAccess getInstance() {
        if (instance == null) instance = new DatabaseAccess();
        return instance;
    }

    // ── Read ─────────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public List<Product> findAllActive() throws SQLException, ClassNotFoundException {
        return findByRetiredStatus(false);
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> findAllRetired() throws SQLException, ClassNotFoundException {
        return findByRetiredStatus(true);
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> findByCode(String code) throws SQLException, ClassNotFoundException {
        List<Product> results = new ArrayList<>();
        String like = "%" + code + "%";

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT product_code, description, price, stock, retired, NULL AS expiration_date FROM " + TABLE_PRODUCTS
                + " WHERE product_code LIKE ?"
                + " UNION ALL "
                + "SELECT product_code, description, price, stock, retired, expiration_date FROM " + TABLE_PERISHABLE
                + " WHERE product_code LIKE ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, like);
                ps.setString(2, like);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    // ── Create ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public void insert(Product product) throws SQLException, ClassNotFoundException {
        boolean perishable = product instanceof PerishableProduct;

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (perishable) {
                try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO " + TABLE_PERISHABLE
                        + " (product_code, description, price, stock, retired, expiration_date)"
                        + " VALUES (?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, product.getCode());
                    ps.setString(2, product.getDescription());
                    ps.setDouble(3, product.getPrice());
                    ps.setInt(4, product.getStock());
                    ps.setBoolean(5, product.isRetired());
                    ps.setString(6, ((PerishableProduct) product).getExpirationDate());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO " + TABLE_PRODUCTS
                        + " (product_code, description, price, stock, retired)"
                        + " VALUES (?, ?, ?, ?, ?)")) {
                    ps.setString(1, product.getCode());
                    ps.setString(2, product.getDescription());
                    ps.setDouble(3, product.getPrice());
                    ps.setInt(4, product.getStock());
                    ps.setBoolean(5, product.isRetired());
                    ps.executeUpdate();
                }
            }
        }
    }

    // ── Update ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public void update(Product product) throws SQLException, ClassNotFoundException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Try products first, then perishable_products
            int rows = executeUpdate(conn, TABLE_PRODUCTS, product);
            if (rows == 0) executeUpdate(conn, TABLE_PERISHABLE, product);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void retire(String code) throws SQLException, ClassNotFoundException {
        setRetiredStatus(code, true);
    }

    /** {@inheritDoc} */
    @Override
    public void unretire(String code) throws SQLException, ClassNotFoundException {
        setRetiredStatus(code, false);
    }

    // ── Delete ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public void delete(String code) throws SQLException, ClassNotFoundException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int rows = executeDelete(conn, TABLE_PRODUCTS, code);
            if (rows == 0) executeDelete(conn, TABLE_PERISHABLE, code);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    /** Queries both tables filtered by {@code retired} status. */
    private List<Product> findByRetiredStatus(boolean retired)
        throws SQLException, ClassNotFoundException {
        List<Product> results = new ArrayList<>();

        String sql = "SELECT product_code, description, price, stock, retired, NULL AS expiration_date FROM " + TABLE_PRODUCTS
            + " WHERE retired = ?"
            + " UNION ALL "
            + "SELECT product_code, description, price, stock, retired, expiration_date FROM " + TABLE_PERISHABLE
            + " WHERE retired = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, retired);
            ps.setBoolean(2, retired);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRow(rs));
            }
        }
        return results;
    }

    /** Updates description, price and stock for a product in {@code table}. */
    private int executeUpdate(Connection conn, String table, Product product)
        throws SQLException {
        String sql = "UPDATE " + table
            + " SET description = ?, price = ?, stock = ?"
            + " WHERE product_code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getDescription());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getCode());
            return ps.executeUpdate();
        }
    }

    /** Sets {@code retired} flag on the product in whichever table contains it. */
    private void setRetiredStatus(String code, boolean retired)
        throws SQLException, ClassNotFoundException {
        String sql = "UPDATE %s SET retired = ? WHERE product_code = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            int rows = executeRetiredUpdate(conn, String.format(sql, TABLE_PRODUCTS), retired, code);
            if (rows == 0) executeRetiredUpdate(conn, String.format(sql, TABLE_PERISHABLE), retired, code);
        }
    }

    private int executeRetiredUpdate(Connection conn, String sql, boolean retired, String code)
        throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, retired);
            ps.setString(2, code);
            return ps.executeUpdate();
        }
    }

    /** Deletes a product from {@code table} by code. Returns affected rows. */
    private int executeDelete(Connection conn, String table, String code)
        throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM " + table + " WHERE product_code = ?")) {
            ps.setString(1, code);
            return ps.executeUpdate();
        }
    }

    /**
     * Maps a ResultSet row to the correct model class.
     * If {@code expiration_date} is present and non-null → PerishableProduct.
     * Otherwise → Product.
     */
    private Product mapRow(ResultSet rs) throws SQLException {
        String expDate = rs.getString("expiration_date");
        Product p = (expDate != null && !expDate.isBlank())
            ? new PerishableProduct(
            rs.getString("product_code"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            expDate)
            : new Product(
            rs.getString("product_code"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock"));
        p.setRetired(rs.getBoolean("retired"));
        return p;
    }
}
