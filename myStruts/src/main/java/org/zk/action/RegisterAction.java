package org.zk.action;

import org.apache.commons.beanutils.BeanUtils;
import org.zk.entity.User;
import org.zk.service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/2.
 */
public class RegisterAction {
    RegisterService registerService = new RegisterService();

    public Object register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object uri = null;
        //封装参数
//        String userName = req.getParameter("userName");
//        String password = req.getParameter("password");
        User user = new User();
//        user.setUserName(userName);
//        user.setPassword(password);
        try {
            BeanUtils.populate(user, req.getParameterMap());
        }catch(Exception e){
            e.printStackTrace();
        }
        //调用service
        boolean result = registerService.register(user);
        //跳转
        if (result) {
            //注册成功
//            uri = req.getRequestDispatcher("/view/login.jsp");
            uri = "/view/login.jsp";
        } else {
            //注册失败
            uri = "redirect:view/register.jsp";
        }
        return uri;
    }
}
