package org.zk.proxy;

import org.zk.dao.UserDao;
import org.zk.service.UserService;
import org.zk.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhangkang on 2016/4/29.
 */
public class JdkProxy implements InvocationHandler{

    private Object target;

    public Object bind(Object target) {
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("===jdk proxy begin===");
        Object result = method.invoke(target, args);
        System.out.println("===jdk proxy end===");
        return result;
    }

    public static void main(String[] args){
        // 代理实现类
        JdkProxy proxy = new JdkProxy();
        UserService userService = (UserService) proxy.bind(new UserServiceImpl());
        userService.findById(1);
        System.out.println(userService.hashCode());
        System.out.println(userService);
        // 代理接口
        // http://blog.csdn.net/zhu_tianwei/article/details/40076391
    }
}
