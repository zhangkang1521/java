package org.zk.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 代理一个接口，不用写具体的实现类
 * http://blog.csdn.net/zhu_tianwei/article/details/40076391
 * Created by root on 16-6-11.
 */
public class MyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("proxy:" + proxy);
        //System.out.println("method:" + method);
        //System.out.println("args:" + Arrays.toString(args));
//        return method.invoke(, args);
        System.out.println("代理接口执行");
       return 1;
    }

}
