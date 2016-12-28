package org.zk.aop;

import org.testng.annotations.Test;
import org.zhangkang.commons.utils.ClassUtils;
import org.zk.aop.jdk.MyInvocationHandler;
import org.zk.aop.jdk.MyInvocationHandler2;

import java.lang.reflect.Proxy;

/**
 * Created by root on 16-6-11.
 */
public class JdkAopTest {

    @Test
    public void testProxyInterface (){
        UserDao proxy = (UserDao)Proxy.newProxyInstance(UserDao.class.getClassLoader(),
                new Class[]{UserDao.class, EmptyInterface.class},
                new MyInvocationHandler());
        System.out.println(proxy.deleteById(10));
        System.out.println(proxy.getClass());
        ClassUtils.showInherit(proxy.getClass());
    }

    @Test
    public void testProxyImpl(){
        UserDao userDao = new UserDaoImpl();
        UserDao proxy = (UserDao)Proxy.newProxyInstance(UserDao.class.getClassLoader(),
                new Class[]{UserDao.class},
                new MyInvocationHandler2(userDao));
        System.out.println(proxy.getClass());
        System.out.println(proxy.deleteById(20));
        System.out.println(proxy.getClass());
    }
}
