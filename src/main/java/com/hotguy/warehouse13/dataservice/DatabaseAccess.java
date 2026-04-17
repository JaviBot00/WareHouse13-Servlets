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

    private static DatabaseAccess instance;

    /** Private constructor enforces Singleton usage. */
    private DatabaseAccess() {}

    /**
     * Returns the single shared instance.
     *
     * @return {@link DatabaseAccess} singleton
     */
    public static synchronized DatabaseAccess getInstance() {
        if (instance == null) instance = new DatabaseAccess();
        return instance;
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        List<Product> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            appendNormalProducts(conn, results);
            appendPerishableProducts(conn, results);
        }
        return results;
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> findByCode(String code) throws SQLException, ClassNotFoundException {
        List<Product> results = new ArrayList<>();
        String like = "%" + code + "%";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Normal products
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Products WHERE code LIKE ?")) {
                ps.setString(1, like);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) results.add(mapProduct(rs));
                }
            }
            // Perishable products
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM PerishableProducts WHERE code LIKE ?")) {
                ps.setString(1, like);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) results.add(mapPerishable(rs));
                }
            }
        }
        return results;
    }

    /** {@inheritDoc} */
    @Override
    public void insert(Product product) throws SQLException, ClassNotFoundException {
        boolean perishable = product instanceof PerishableProduct;
        String table  = perishable ? "PerishableProducts" : "Products";
        String sql    = perishable
            ? "INSERT INTO " + table + " (code, description, price, stock, expiration_date) VALUES (?, ?, ?, ?, ?)"
            : "INSERT INTO " + table + " (code, description, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getCode());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            if (perishable) ps.setString(5, ((PerishableProduct) product).getExpirationDate());

            ps.executeUpdate();
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    /** Queries the Products table and appends results to {@code list}. */
    private void appendNormalProducts(Connection conn, List<Product> list)
            throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery("SELECT * FROM Products")) {
            while (rs.next()) list.add(mapProduct(rs));
        }
    }

    /** Queries the PerishableProducts table and appends results to {@code list}. */
    private void appendPerishableProducts(Connection conn, List<Product> list)
            throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery("SELECT * FROM PerishableProducts")) {
            while (rs.next()) list.add(mapPerishable(rs));
        }
    }

    /** Maps a {@link ResultSet} row to a {@link Product}. */
    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getString("code"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock")
        );
    }

    /** Maps a {@link ResultSet} row to a {@link PerishableProduct}. */
    private PerishableProduct mapPerishable(ResultSet rs) throws SQLException {
        return new PerishableProduct(
            rs.getString("code"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            rs.getString("expiration_date")
        );
    }
}
