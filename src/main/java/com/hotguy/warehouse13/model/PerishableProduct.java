package com.hotguy.warehouse13.model;

/** Extends {@link Product} with an expiration date. */
public class PerishableProduct extends Product {

    private String expirationDate;

    /**
     * @param code           unique product code
     * @param description    human-readable name
     * @param price          unit price (must be >= 0)
     * @param stock          initial stock quantity
     * @param expirationDate expiration date as ISO string (yyyy-MM-dd)
     */
    public PerishableProduct(String code, String description, double price,
                             int stock, String expirationDate) {
        super(code, description, price, stock);
        this.expirationDate = expirationDate;
    }

    /** @return expiration date as ISO string */
    public String getExpirationDate() { return expirationDate; }

    /** @param expirationDate expiration date as ISO string (yyyy-MM-dd) */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
