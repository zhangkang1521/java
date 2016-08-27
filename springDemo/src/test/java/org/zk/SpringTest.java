package org.zk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.beans.User;

/**
 * Created by Administrator on 8/27/2016.
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User)ctx.getBean("user");
        System.out.println(user);
    }
}
