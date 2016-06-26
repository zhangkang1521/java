package org.zk.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;
import org.zk.entity.User;

/**
 * Created by root on 6/26/16.
 */
public class UserDaoTest extends BaseTest {

    @Autowired
    UserDao userDao;

    @Test
    public void testSave(){
        User user = new User();
        user.setAge(2);
        user.setUserName("zk");
        userDao.save(user);
    }
}
