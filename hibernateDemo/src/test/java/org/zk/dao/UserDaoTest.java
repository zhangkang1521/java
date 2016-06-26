package org.zk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zk.entity.User;

/**
 * Created by zhangkang on 2016/6/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void test1(){
        userDao.findById(1);
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUserName("zk1");
        userDao.save(user);
    }
}
