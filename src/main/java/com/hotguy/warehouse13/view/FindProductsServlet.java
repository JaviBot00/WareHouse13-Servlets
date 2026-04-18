package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/** GET /buscar?code=XX — Searches products by code regardless of retired status. */
public class FindProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, new Controller().findProductsByCode(code));
    }
}
