package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that inserts a product.
 * Passes raw parameters to the Controller — no model construction here.
 */
public class InsertProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String code           = req.getParameter("code");
        String description    = req.getParameter("description");
        double price          = Double.parseDouble(req.getParameter("price"));
        int    stock          = Integer.parseInt(req.getParameter("stock"));
        String expirationDate = req.getParameter("expirationDate"); // null if absent

        ServletUtils.writeJson(res, new Controller().insertProduct(
            code, description, price, stock, expirationDate
        ));
    }
}
