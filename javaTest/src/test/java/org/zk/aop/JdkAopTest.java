package org.zk.aop;

import org.testng.annotations.Test;
import org.zhangkang.commons.utils.ClassUtils;
import org.zhangkang.entity.User;
import org.zk.aop.jdk.MyInvocationHandler;
import org.zk.aop.jdk.MyInvocationHandler2;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by root on 16-6-11.
 */
public class JdkAopTest {

    @Test
    public void testProxyInterface () throws Exception{
        UserDao proxy = (UserDao)Proxy.newProxyInstance(UserDao.class.getClassLoader(),
                new Class[]{UserDao.class, EmptyInterface.class},
                new MyInvocationHandler());
        System.out.println(proxy.deleteById(10));
        System.out.println(proxy.getClass());
//        ClassUtils.showInherit(proxy.getClass());
//        Class c = proxy.getClass();
//        Method[] methods = c.getMethods();
//        for (int i = 0; i < methods.length; i++) {
//            System.out.println(methods[i]);
//        }
      // TODO 将字节码保存到本地，用jd-gui查看deleteById方法调用invoke
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
