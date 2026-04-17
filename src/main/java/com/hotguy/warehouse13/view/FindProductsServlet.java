package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet that searches products by code and returns results as JSON. */
public class FindProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, new Controller().findProductsByCode(code));
    }
}
