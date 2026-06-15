package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * PUT /actualizar
 * <p>
 * Body params: code, description, price, stock
 * <p>
 * Returns {@code 400 Bad Request} if the product code does not meet
 * the length requirements enforced by the model.
 */
public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            String code        = req.getParameter("code");
            String description = req.getParameter("description");
            double price       = Double.parseDouble(req.getParameter("price"));
            int    stock       = Integer.parseInt(req.getParameter("stock"));

            ServletUtils.writeJson(res, new Controller().updateProduct(
                code, description, price, stock));

        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletUtils.writeJson(res, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
