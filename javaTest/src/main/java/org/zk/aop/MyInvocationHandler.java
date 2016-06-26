package org.zk.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by root on 16-6-11.
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(){

    }

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("proxy:" + proxy);
        System.out.println("method:" + method);
        System.out.println("args:" + Arrays.toString(args));
        //return method.invoke(, args);
       return "ss";
    }

    public static <T> T newMapperProxy(Class<T> mapperInterface) {
        ClassLoader classLoader = mapperInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapperInterface};
        MyInvocationHandler proxy = new MyInvocationHandler();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }


}
