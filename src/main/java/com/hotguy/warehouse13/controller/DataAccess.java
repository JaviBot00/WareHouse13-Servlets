package com.hotguy.warehouse13.controller;

public interface DataAccess {

    String listAllProducts();

    String findProductsByCode(String code);

    String insertProduct(String jsonProduct, boolean expired);

}
