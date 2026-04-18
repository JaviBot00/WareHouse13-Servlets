package com.hotguy.warehouse13.model;

/** Represents a standard warehouse product. */
public class Product {

    private String code;
    private String description;
    private double price;
    private int stock;
    private boolean retired;

    /**
     * @param code        unique product code
     * @param description human-readable name
     * @param price       unit price (must be >= 0)
     * @param stock       initial stock quantity
     */
    public Product(String code, String description, double price, int stock) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.retired = false;
    }

    /** @return unique product code */
    public String getCode() { return code; }

    /** @param code unique product code */
    public void setCode(String code) { this.code = code; }

    /** @return product description */
    public String getDescription() { return description; }

    /** @param description product description */
    public void setDescription(String description) { this.description = description; }

    /** @return unit price */
    public double getPrice() { return price; }

    /** @param price unit price; ignored if negative */
    public void setPrice(double price) {
        if (price >= 0.0) this.price = price;
    }

    /** @return current stock quantity */
    public int getStock() { return stock; }

    /**
     * Adjusts stock by {@code delta}. Negative values reduce stock.
     * Operation is ignored if it would result in negative stock.
     *
     * @param delta units to add (positive) or remove (negative)
     */
    public void changeStock(int delta) {
        if (this.stock + delta >= 0) this.stock += delta;
    }

    /** @return true if this product has been retired from the warehouse */
    public boolean isRetired() { return retired; }

    /** @param retired true to mark as retired, false to reactivate */
    public void setRetired(boolean retired) { this.retired = retired; }
}
