package org.zk.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;
import org.zk.entity.User;

/**
 * Created by root on 6/26/16.
 */
public class UserServiceTest extends BaseTest{

    @Autowired
    UserService userService;

    @Test
    public void save(){
        User user = new User();
        try {
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
