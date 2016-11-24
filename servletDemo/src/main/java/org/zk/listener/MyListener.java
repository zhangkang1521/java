package org.zk.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 9/17/2016.
 */
public class MyListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("root", "application is a type of javax.servlet.ServletContext");
        System.out.println(sc.getAttribute("root"));
        // servlet 2.5
        System.out.println(sc.getMajorVersion());
        System.out.println(sc.getMinorVersion());
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
