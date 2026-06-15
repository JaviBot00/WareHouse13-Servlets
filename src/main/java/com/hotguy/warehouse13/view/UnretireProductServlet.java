package com.hotguy.warehouse13.view;

import com.hotguy.warehouse13.controller.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * PUT /reactivar
 * <p>
 * Body params: code
 * <p>
 * Returns {@code 400 Bad Request} if the product code does not meet
 * the length requirements enforced by the model.
 */
public class UnretireProductServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            String code = req.getParameter("code");
            ServletUtils.writeJson(res, new Controller().unretireProduct(code));

        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletUtils.writeJson(res, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
