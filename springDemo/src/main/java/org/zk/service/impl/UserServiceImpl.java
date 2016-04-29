package org.zk.service.impl;

import org.springframework.stereotype.Service;
import org.zk.beans.User;
import org.zk.service.UserService;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Service
public class UserServiceImpl implements UserService {


    public User findById(Integer id) {
        User user = new User();
        user.setId(id);
        System.out.println("findById"+id);
        System.out.println(this.hashCode());
        return user;
    }

}
