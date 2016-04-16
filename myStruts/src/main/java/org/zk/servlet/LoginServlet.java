package org.zk.servlet;

import org.zk.action.LoginAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/2.
 */
public class LoginServlet extends HttpServlet {

//    LoginService loginService = new LoginService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginAction loginAction = new LoginAction();
        Object uri = loginAction.login(req, resp);
        if(uri instanceof String){
            resp.sendRedirect((String)uri);
        } else {
            ((javax.servlet.RequestDispatcher)uri).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
