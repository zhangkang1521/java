package org.zk.servlet;

import org.zk.action.LoginAction;
import org.zk.action.RegisterAction;
import org.zk.entity.User;
import org.zk.service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/2.
 */
public class RegisterServlet extends HttpServlet {

//    RegisterService registerService = new RegisterService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterAction registerAction = new RegisterAction();
        Object uri = registerAction.register(req, resp);
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
