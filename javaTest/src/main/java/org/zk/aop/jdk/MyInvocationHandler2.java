package org.zk.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 代理一个实现类，但实现类必须实现接口
 * Created by Administrator on 9/11/2016.
 */
public class MyInvocationHandler2 implements InvocationHandler {

    private Object target;

    public MyInvocationHandler2(){

    }

    public MyInvocationHandler2(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("proxy:" + proxy);
        System.out.println("method:" + method);
        System.out.println("args:" + Arrays.toString(args));
        System.out.println("***** before 实现类 *****");
        Object result =  method.invoke(target, args);
        System.out.println("***** after 实现类 *****");
        return result;
    }
}
