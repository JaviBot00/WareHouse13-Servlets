package com.hotguy.warehouse13.dataservice;

import com.hotguy.warehouse13.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Contract for all data persistence operations.
 * Decouples the {@link com.hotguy.warehouse13.controller.Controller}
 * from any concrete storage implementation.
 */
public interface DataRepository {

    // ── Read ─────────────────────────────────────────────────────────────────

    /**
     * Returns all active (non-retired) products from both tables.
     *
     * @return list of active products (normal and perishable)
     * @throws SQLException           on query failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    List<Product> findAllActive() throws SQLException, ClassNotFoundException;

    /**
     * Returns all retired products from both tables.
     *
     * @return list of retired products (normal and perishable)
     * @throws SQLException           on query failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    List<Product> findAllRetired() throws SQLException, ClassNotFoundException;

    /**
     * Returns all products whose code contains {@code code}.
     *
     * @param code partial or full product code to match
     * @return matching products regardless of retired status
     * @throws SQLException           on query failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    List<Product> findByCode(String code) throws SQLException, ClassNotFoundException;

    // ── Create ───────────────────────────────────────────────────────────────

    /**
     * Persists a new product to the appropriate table.
     *
     * @param product product to insert
     * @throws SQLException           on insert failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void insert(Product product) throws SQLException, ClassNotFoundException;

    // ── Update ───────────────────────────────────────────────────────────────

    /**
     * Updates description, price and stock of an existing product.
     * Identifies the record by product code across both tables.
     *
     * @param product product with updated values (code is the key)
     * @throws SQLException           on update failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void update(Product product) throws SQLException, ClassNotFoundException;

    /**
     * Marks a product as retired ({@code retired = true}).
     *
     * @param code product code to retire
     * @throws SQLException           on update failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void retire(String code) throws SQLException, ClassNotFoundException;

    /**
     * Marks a retired product as active again ({@code retired = false}).
     *
     * @param code product code to reactivate
     * @throws SQLException           on update failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void unretire(String code) throws SQLException, ClassNotFoundException;

    // ── Delete ───────────────────────────────────────────────────────────────

    /**
     * Physically deletes a product from storage.
     * Searches both tables — deletes from whichever contains the code.
     *
     * @param code product code to delete
     * @throws SQLException           on delete failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void delete(String code) throws SQLException, ClassNotFoundException;
}
