package org.zk.action;

import org.zk.entity.User;
import org.zk.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/2.
 */
public class LoginAction {

    LoginService loginService = new LoginService();

    public Object login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object uri = null;
        //封装参数
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        //调用service
        boolean result = loginService.login(user);
        //跳转

        if(result){
            //登录成功
            req.getSession().setAttribute("user", user);
//            resp.sendRedirect("index.jsp");
            uri = "redirect:index.jsp";
        } else {
            //登录失败
//            req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
//            uri = req.getRequestDispatcher("/view/login.jsp");
            uri = "/view/login.jsp";
        }
        return uri;
    }
}
