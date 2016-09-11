package org.zk.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.BaseTest;
import org.zk.beans.User;
import org.zk.dao.UserDao;

/**
 * Created by zhangkang on 2016/4/27.
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService service;

    @Test
    public void testFind() {
        service.findById(1);
    }


}
