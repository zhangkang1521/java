package org.zk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zk.dao.UserDao;
import org.zk.entity.User;

/**
 * Created by zhangkang on 2016/9/5.
 */
@Service
public abstract class AbstractUserService implements UserService{

    @Autowired
    private UserDao userDao;

    public int  save(User user){
        userDao.save(user);
        return 1;
    }
}
