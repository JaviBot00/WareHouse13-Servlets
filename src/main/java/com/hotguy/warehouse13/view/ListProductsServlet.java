package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet that lists all warehouse products as JSON. */
public class ListProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ServletUtils.writeJson(res, new Controller().listAllProducts());
    }
}
