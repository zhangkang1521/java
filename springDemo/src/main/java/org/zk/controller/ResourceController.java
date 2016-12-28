package org.zk.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 12/5/2016.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping("/test")
    public void test(HttpServletRequest request) throws Exception{
        ServletContext servletContext = request.getSession().getServletContext();
        // 获取web目录下的资源
        Resource res1 = new ServletContextResource(servletContext, "/WEB-INF/web.xml");
        System.out.println(res1.exists());
        System.out.println(res1.getFile());
        // 获取spring容器中的bean
        WebApplicationContext wac = WebApplicationContextUtils. getWebApplicationContext(servletContext);
        ResourceController controller = (ResourceController) wac.getBean("resourceController");
    }
}
