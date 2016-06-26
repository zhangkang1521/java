package org.zk.aop;

import org.zhangkang.entity.User;

/**
 * Created by root on 16-6-11.
 */
public class UserDaoImpl implements UserDao {
    public int deleteById(int id) {
        System.out.println("deleteById");
        return 0;
    }
}
