package org.zk.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;

/**
 * Created by zhangkang on 2016/4/27.
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    UserDao userDao;

    @Test
    public void test1(){
        System.out.println(userDao.getClass());
        userDao.deleteById(2);
    }
}
