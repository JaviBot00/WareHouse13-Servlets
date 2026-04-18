package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * PUT /actualizar
 * Body params: code, description, price, stock
 */
public class UpdateProductServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code        = req.getParameter("code");
        String description = req.getParameter("description");
        double price       = Double.parseDouble(req.getParameter("price"));
        int    stock       = Integer.parseInt(req.getParameter("stock"));

        ServletUtils.writeJson(res, new Controller().updateProduct(
            code, description, price, stock));
    }
}
