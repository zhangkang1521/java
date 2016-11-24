package org.zk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 10/24/2016.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("login")
    public String login() {
        return "/login/login";
    }
}
