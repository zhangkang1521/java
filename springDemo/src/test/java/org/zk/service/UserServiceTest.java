package org.zk.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.BaseTest;
import org.zk.beans.User;
import org.zk.dao.UserDao;
import org.zk.utils.SpringFactory;

/**
 * Created by zhangkang on 2016/4/27.
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService service;

    @Test
    public void testFind() {
        new Thread(new MyThread(service)).start();
//        new MyThread(service).start();
    }

    static class MyThread implements Runnable{
        UserService service;
        public MyThread(UserService service){
            this.service = service;
        }

        public void run() {
			System.out.println("ss");
            service.testLock("b");
        }
    }
}
