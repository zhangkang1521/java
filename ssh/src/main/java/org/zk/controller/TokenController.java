package org.zk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zk.interceptor.Token;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by root on 7/31/16.
 */
@Controller
@RequestMapping("/token")
public class TokenController {

    @RequestMapping("tokenForm")
    @Token(save = true)
    public void tokenForm(){

    }

    @RequestMapping("submit")
    @Token(remove = true)
    public void submit(HttpServletRequest request){
        System.out.println(request.getParameter("username"));
    }
}
