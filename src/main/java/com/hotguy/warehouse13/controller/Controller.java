package com.hotguy.warehouse13.controller;

import com.google.gson.Gson;
import com.hotguy.warehouse13.dataservice.DatabaseAccess;
import com.hotguy.warehouse13.dataservice.DataRepository;
import com.hotguy.warehouse13.model.PerishableProduct;
import com.hotguy.warehouse13.model.Product;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Mediates between the View (Servlets) and the data layer.
 * Accepts raw parameters and returns JSON strings — the View
 * never builds or parses model objects directly.
 */
public class Controller implements ControllerContract {

    private static final Gson GSON = new Gson();

    private final DataRepository repository;

    /**
     * Production constructor — uses the shared {@link DatabaseAccess} singleton.
     */
    public Controller() {
        this(DatabaseAccess.getInstance());
    }

    /**
     * Injectable constructor for testing or alternative data sources.
     *
     * @param repository data access implementation to use
     */
    public Controller(DataRepository repository) {
        this.repository = repository;
    }

    /** {@inheritDoc} */
    @Override
    public String listAllProducts() {
        try {
            return GSON.toJson(repository.findAll());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listAllProducts", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String findProductsByCode(String code) {
        try {
            return GSON.toJson(repository.findByCode(code));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findProductsByCode", e);
        }
    }

    /**
     * {@inheritDoc}
     * Builds the correct model object here — the View passes raw values only.
     */
    @Override
    public String insertProduct(String code, String description,
            double price, int stock, String expirationDate) {
        Product product = (expirationDate != null && !expirationDate.isBlank())
                ? new PerishableProduct(code, description, price, stock, expirationDate)
                : new Product(code, description, price, stock);

        try {
            repository.insert(product);
            return GSON.toJson(Collections.singletonMap("status", "OK"));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("insertProduct", e);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    /** Builds a consistent JSON error response. */
    private String errorJson(String operation, Exception e) {
        return GSON.toJson(Collections.singletonMap(
                "error", operation + ": " + e.getMessage()));
    }
}
