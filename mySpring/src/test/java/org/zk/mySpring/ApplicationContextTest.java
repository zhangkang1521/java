package org.zk.mySpring;

import org.junit.Assert;
import org.junit.Test;
import org.zk.mySpring.entitys.Pen;
import org.zk.mySpring.entitys.User;


/**
 * Created by Administrator on 2016/4/23.
 */
public class ApplicationContextTest {

    @Test
    public void test1(){
        ApplicationContext ctx = new XmlApplicationContext("applicationContext.xml");
        User user = (User)ctx.getBean("user1");
        System.out.println(user);
//        ApplicationContext ctx = new XmlApplicationContext("applicationContext.xml");
        Pen pen = (Pen)ctx.getBean("pen1");
        System.out.println(pen);
        Assert.assertTrue(user.getPen()==pen);
    }
}
