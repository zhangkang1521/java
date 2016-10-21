package org.zk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
        //throw new RuntimeException("ss");
        //System.out.println("query user list");
    }

    @RequestMapping("del")
    @ResponseBody
    public String del(HttpServletResponse response){
        response.setContentType("application/json");
       // throw new RuntimeException("s");
       return "{\"id\":\"100\"}";
    }


}
