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

    /** Production constructor — uses the shared {@link DatabaseAccess} singleton. */
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

    // ── Read ─────────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public String listAllActive() {
        try {
            return GSON.toJson(repository.findAllActive());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listAllActive", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String listAllRetired() {
        try {
            return GSON.toJson(repository.findAllRetired());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listAllRetired", e);
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

    // ── Create ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public String insertProduct(String code, String description,
                                double price, int stock, String expirationDate) {
        Product product = (expirationDate != null && !expirationDate.isBlank())
            ? new PerishableProduct(code, description, price, stock, expirationDate)
            : new Product(code, description, price, stock);
        try {
            repository.insert(product);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("insertProduct", e);
        }
    }

    // ── Update ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public String updateProduct(String code, String description, double price, int stock) {
        Product product = new Product(code, description, price, stock);
        try {
            repository.update(product);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("updateProduct", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String retireProduct(String code) {
        try {
            repository.retire(code);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("retireProduct", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String unretireProduct(String code) {
        try {
            repository.unretire(code);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("unretireProduct", e);
        }
    }

    // ── Delete ───────────────────────────────────────────────────────────────

    /** {@inheritDoc} */
    @Override
    public String deleteProduct(String code) {
        try {
            repository.delete(code);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("deleteProduct", e);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private String okJson() {
        return GSON.toJson(Collections.singletonMap("status", "OK"));
    }

    private String errorJson(String operation, Exception e) {
        return GSON.toJson(Collections.singletonMap(
            "error", operation + ": " + e.getMessage()));
    }
}
