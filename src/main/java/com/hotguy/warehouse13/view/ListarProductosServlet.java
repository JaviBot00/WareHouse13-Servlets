package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ListarProductosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();

        out.println((new Controlador()).listAllProducts());
    }
}
