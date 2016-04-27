package org.zk.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.beans.User;

/**
 * Created by zhangkang on 2016/4/27.
 */
public class UserServiceTest {



    @Test
    public void testFind(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = ctx.getBean("userServiceImpl", UserService.class);
        System.out.println(userService.getClass());
        User user = userService.findById(1);
        System.out.println(user.getId());
    }
}
