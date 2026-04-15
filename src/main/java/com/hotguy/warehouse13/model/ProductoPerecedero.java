package com.hotguy.warehouse13.model;

public class ProductoPerecedero extends Producto {
    private String ExpDate;

    public ProductoPerecedero(String codigoProducto, String descripcion, double precio, int stock, String ExpDate) {
        super(codigoProducto, descripcion, precio, stock);
        this.ExpDate = ExpDate;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String ExpDate) {
        this.ExpDate = ExpDate;
    }

    @Override
    public String toString() {
        return "ProductoPerecedero," +
            getCodigoProducto() + "," +
            getDescripcion() + "," +
            getPrecio() + "," +
            getStock() + "," +
            getExpDate();
    }
}
