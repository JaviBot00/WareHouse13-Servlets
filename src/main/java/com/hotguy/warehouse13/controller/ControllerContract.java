package com.hotguy.warehouse13.controller;

/**
 * Contract exposed by the Controller to the View layer.
 * All responses are JSON strings.
 */
public interface ControllerContract {

    /** @return JSON array of all products in the warehouse */
    String listAllProducts();

    /**
     * @param code partial or full product code to search
     * @return JSON array of matching products
     */
    String findProductsByCode(String code);

    /**
     * Inserts a new product into the warehouse.
     *
     * @param code           product code
     * @param description    product description
     * @param price          unit price
     * @param stock          initial stock
     * @param expirationDate ISO date string, or {@code null} for normal products
     * @return JSON status message
     */
    String insertProduct(String code, String description,
                         double price, int stock, String expirationDate);
}
