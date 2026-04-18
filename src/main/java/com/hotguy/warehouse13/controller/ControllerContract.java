package com.hotguy.warehouse13.controller;

/**
 * Contract exposed by the Controller to the View layer (Servlets).
 * All responses are JSON strings.
 */
public interface ControllerContract {

    // ── Read ─────────────────────────────────────────────────────────────────

    /** @return JSON array of all active (non-retired) products */
    String listAllActive();

    /** @return JSON array of all retired products */
    String listAllRetired();

    /**
     * @param code partial or full product code to search
     * @return JSON array of matching products regardless of retired status
     */
    String findProductsByCode(String code);

    // ── Create ───────────────────────────────────────────────────────────────

    /**
     * Inserts a new product into the warehouse.
     *
     * @param code           product code
     * @param description    product description
     * @param price          unit price
     * @param stock          initial stock
     * @param expirationDate YYYYMMDD string, or {@code null} for normal products
     * @return JSON status message
     */
    String insertProduct(String code, String description,
                         double price, int stock, String expirationDate);

    // ── Update ───────────────────────────────────────────────────────────────

    /**
     * Updates description, price and stock of an existing product.
     *
     * @param code        product code (key — not updated)
     * @param description new description
     * @param price       new price
     * @param stock       new absolute stock value
     * @return JSON status message
     */
    String updateProduct(String code, String description, double price, int stock);

    /**
     * Marks a product as retired.
     *
     * @param code product code to retire
     * @return JSON status message
     */
    String retireProduct(String code);

    /**
     * Reactivates a retired product.
     *
     * @param code product code to reactivate
     * @return JSON status message
     */
    String unretireProduct(String code);

    // ── Delete ───────────────────────────────────────────────────────────────

    /**
     * Physically deletes a product from storage.
     *
     * @param code product code to delete
     * @return JSON status message
     */
    String deleteProduct(String code);
}
