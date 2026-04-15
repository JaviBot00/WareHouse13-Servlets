package com.hotguy.warehouse13.controller;

import com.google.gson.Gson;
import com.hotguy.warehouse13.dataservice.BBDDAccess;
import com.hotguy.warehouse13.model.Producto;
import com.hotguy.warehouse13.model.ProductoPerecedero;

import java.sql.SQLException;
import java.util.List;

public class Controlador implements DataAccess {

    public Controlador() {
    }

    //Ahora, el controlador sólo se preocupa de ser el intermediario entre
    //la vista (servlet) y el acceso a datos, usando clases sencillas del modelo
    //Para que la vista desde el backend sea independiente de formato, usamos JSON
    @Override
    public String listAllProducts() {
        BBDDAccess bbdd = new BBDDAccess();

        try {
            return (new Gson()).toJson(bbdd.listarTodos());
        } catch (SQLException | ClassNotFoundException se) {
            return "List Products: " + se.getMessage();
        }
    }

    @Override
    public String findProductsByCode(String code) {
        BBDDAccess bbdd = new BBDDAccess();

        try {
            return (new Gson()).toJson(bbdd.buscarPorCodigo(code));
        } catch (SQLException | ClassNotFoundException se) {
            return "Find Products: " + code + " - " + se.getMessage();
        }
    }

    @Override
    public String insertProduct(String jsonProducto, boolean perecedero) {
        Gson gson = new Gson();
        Producto producto;
        if (perecedero) {
            producto = gson.fromJson(jsonProducto, ProductoPerecedero.class);
        } else producto = gson.fromJson(jsonProducto, Producto.class);
        BBDDAccess bbdd = new BBDDAccess();

        try {
            bbdd.insertarProducto(producto);

        } catch (SQLException se) {
            return "Product: " + producto.getCodigoProducto() + "Insert: " + jsonProducto + "Expired:" + perecedero + " - " + se.getMessage();
        } catch (ClassNotFoundException c) {
            return "Insert: " + jsonProducto + " - " + c.getMessage();
        }
        return "Inserción realizada OK!";
    }
}
