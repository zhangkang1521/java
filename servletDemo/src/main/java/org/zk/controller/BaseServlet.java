package org.zk.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 7/16/16.
 */
public class BaseServlet extends HttpServlet {

    /**
     * 设置contentType为json
     * @param response
     */
    public void setContentTypeJson(HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");
    }


}
