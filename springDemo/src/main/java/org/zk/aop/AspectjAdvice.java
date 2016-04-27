package org.zk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Aspect // 这个不能少
@Component
public class AspectjAdvice {

    @Pointcut("execution(* org.zk.service..*.*(..))")
    private void servicePointCut(){};

    @Before("servicePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        System.out.println("-----@Before-----");
    }

}
