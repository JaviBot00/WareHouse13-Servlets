package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/** GET /listar-activos — Returns all active products as JSON. */
public class ListActiveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServletUtils.writeJson(res, new Controller().listAllActive());
    }
}
