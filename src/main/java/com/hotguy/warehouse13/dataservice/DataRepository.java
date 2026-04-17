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

    /**
     * Returns all products from storage.
     *
     * @return list of all products (normal and perishable)
     * @throws SQLException           on query failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    List<Product> findAll() throws SQLException, ClassNotFoundException;

    /**
     * Returns all products whose code contains {@code code}.
     *
     * @param code partial or full product code to match
     * @return matching products
     * @throws SQLException           on query failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    List<Product> findByCode(String code) throws SQLException, ClassNotFoundException;

    /**
     * Persists a new product to storage.
     *
     * @param product product to insert
     * @throws SQLException           on insert failure
     * @throws ClassNotFoundException if the JDBC driver is missing
     */
    void insert(Product product) throws SQLException, ClassNotFoundException;
}
