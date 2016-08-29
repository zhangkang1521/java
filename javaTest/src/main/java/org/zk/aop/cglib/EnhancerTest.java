package org.zk.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zhangkang on 2016/8/29.
 */
public class EnhancerTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        UserDaoImpl userDao = (UserDaoImpl) enhancer.create();
        int result = userDao.deleteById(100);
        System.out.println(result);
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {
        public Object intercept(Object obj,
                                Method method,
                                Object[] args,
                                MethodProxy proxy) throws Throwable {
            System.out.println("========== start ==========");
            System.out.println(Arrays.toString(args));
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("=========== end =========");
            return result;
        }
    }
}
