package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * POST /insertar
 * Body params: code, description, price, stock, expirationDate (optional)
 */
public class InsertProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code           = req.getParameter("code");
        String description    = req.getParameter("description");
        double price          = Double.parseDouble(req.getParameter("price"));
        int    stock          = Integer.parseInt(req.getParameter("stock"));
        String expirationDate = req.getParameter("expirationDate");

        ServletUtils.writeJson(res, new Controller().insertProduct(
            code, description, price, stock, expirationDate));
    }
}
