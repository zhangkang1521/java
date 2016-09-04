package org.zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zk.service.UserService;
import org.zk.service.impl.UserServiceImpl;

/**
 * Created by Administrator on 9/4/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //@Autowired
   // UserServiceImpl userService;

    @RequestMapping("list")
    public void list(){
        System.out.println("query user list");
    }


}
