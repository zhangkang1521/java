package org.zk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.zhangkang.commons.utils.DbUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by zhangkang on 2016/4/27.
 * dao层切面，实现简单mybatis功能
 */
@Aspect
@Component
public class DaoAspect {

    @Pointcut("execution(* org.zk.dao..*.*(..))")
    private void daoPointCut(){}



    @Around("daoPointCut()")
    public Object executeSql(ProceedingJoinPoint pjp){
        DbUtils dbUtils = null;
        Object result = null;
        try {
            dbUtils = new DbUtils();
            Object[] args = pjp.getArgs();
            MethodSignature signature = (MethodSignature)pjp.getSignature();
            Method method = signature.getMethod();
            Sql sqlAnno = method.getAnnotation(Sql.class);
            String sql = sqlAnno.value();
            if(sqlAnno.type()== Sql.SqlType.UPDATE){
                // 更新
                int rowAffect = dbUtils.executeUpdate(sql, args);
                result = rowAffect;
            } else if(sqlAnno.type() == Sql.SqlType.QUERY){
                // 查询
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if(dbUtils!=null) {
                dbUtils.close();
            }
        }
        return result;
    }
}
