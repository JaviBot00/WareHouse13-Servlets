package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * DELETE /eliminar
 * Body params: code
 */
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, new Controller().deleteProduct(code));
    }
}
