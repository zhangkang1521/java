package org.zk.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 9/17/2016.
 */
public class MyListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        application.setAttribute("root", "application is a type of javax.servlet.ServletContext");
        System.out.println(application.getAttribute("root"));
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
