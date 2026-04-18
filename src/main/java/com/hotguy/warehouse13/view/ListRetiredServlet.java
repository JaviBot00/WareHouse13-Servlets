package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/** GET /listar-retirados — Returns all retired products as JSON. */
public class ListRetiredServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServletUtils.writeJson(res, new Controller().listAllRetired());
    }
}
