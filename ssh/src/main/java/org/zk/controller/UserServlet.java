package org.zk.controller;

import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangkang on 2016/5/10.
 */
public class UserServlet extends HttpServlet{

    private static final Logger logger = Logger.getLogger(UserServlet.class);
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("log4j-over-slf4j xxd debug");
        logger.warn("xxd warn");
        logger.error("xxd error");
        LOG.debug("xx");
    }
}
