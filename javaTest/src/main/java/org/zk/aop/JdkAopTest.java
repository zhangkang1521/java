package org.zk.aop;

import java.lang.reflect.Proxy;

/**
 * Created by root on 16-6-11.
 */
public class JdkAopTest {
    public static void main(String[] args) {
        UserDao proxy = (UserDao)Proxy.newProxyInstance(UserDao.class.getClassLoader(),
                new Class[]{UserDao.class},
                new MyInvocationHandler());
        System.out.println(proxy);
//        UserDao dao = MyInvocationHandler.newMapperProxy(UserDao.class);
//        System.out.println(dao.deleteById(1));
    }
}
