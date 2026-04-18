package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * PUT /reactivar
 * Body params: code
 */
public class UnretireProductServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, new Controller().unretireProduct(code));
    }
}
