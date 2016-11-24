package org.zk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zk.beans.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/24/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("list")
    public ModelAndView list(Model model){
        model.addAttribute("pageSize", 20);
        // 用户列表
        List<User> userList = new ArrayList<User>();
        for(int i=0; i< 10; i++) {
            User user = new User();
            user.setId(i);
            user.setUserName("zk"+i);
            user.setAge(10+i);
            userList.add(user);
        }
        model.addAttribute("list", userList);

        ModelAndView modelAndView = new ModelAndView("user/list");
        return modelAndView;
    }

//    @RequestMapping("list")
//    public ModelAndView list2(){
//        ModelAndView modelAndView = new ModelAndView("user/list2");
//        return modelAndView;
//    }
}
