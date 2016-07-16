package org.zk.controller;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 7/2/16.
 */
public class XssServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
//        String content = "<script>alert('11')</script>";

//        req.setAttribute("content", StringEscapeUtils.escapeHtml4(content));
        req.setAttribute("content", content);
        req.getRequestDispatcher("/xss.jsp").forward(req, resp);
    }
}
