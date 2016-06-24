package org.zhangkang.commons.mock;

import org.zhangkang.entity.User;

/**
 * Created by root on 16-6-5.
 */
public class UserDao {

    public User findById(int id){
        return new User();
    }
}
