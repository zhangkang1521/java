package org.zk.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理
 */
@Component
public class CommonMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CommonMappingExceptionResolver.class);


    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                           Object handler, Exception ex) {
        logger.error("controller 错误", ex);
        ModelAndView modelAndView = new ModelAndView("common/error");
        return modelAndView;
    }

}
