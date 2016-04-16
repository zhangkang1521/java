package org.zk.framework.action;

import org.zk.commons.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 核心控制器
 */
public class ActionServlet extends HttpServlet {

    /** 对应myStruts.xml配置 */
    private ActionMap actionMap = null;

    /**方法返回结果以此字符串开头，用以标示此路径是否需要redirect*/
    public static final String REDIRECT = "redirect:";

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("==============核心控制器初始化配置==================");
        actionMap = new ActionMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //1. 根据请求路径得到Action的类名和方法名 login.action -> LoginAction.login
            String ctx = req.getContextPath();
            String uri = StringUtils.removeContextPath(ctx, req.getRequestURI());
            ActionInfo actionInfo = actionMap.get(uri);
            String classPath = actionInfo.getClassPath();
            String methodName = actionInfo.getMethodName();

            //2. 通过反射调用此方法
            Class<?> cls = Class.forName(classPath);
            Method method = cls.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            Object result = method.invoke(cls.newInstance(), req, resp);

            //3. 根据方法的返回值进行页面跳转
            if(result instanceof String) {
                String resultStr = (String)result;
                if (resultStr.startsWith(REDIRECT)) {
                    resp.sendRedirect(resultStr.substring(REDIRECT.length()));
                } else {
                    req.getRequestDispatcher(resultStr).forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
