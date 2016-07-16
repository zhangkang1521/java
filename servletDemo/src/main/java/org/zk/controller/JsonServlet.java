package org.zk.controller;

import com.alibaba.fastjson.JSON;
import org.zk.dto.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 7/16/16.
 */
public class JsonServlet extends BaseServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.setContentTypeJson(resp);
        PrintWriter pw = resp.getWriter();

        String uri = req.getRequestURI();

        Result result = new Result();
        pw.write(JSON.toJSONString(result));
        pw.close();
    }
}
