package org.zk.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sql {

    public enum SqlType{QUERY, UPDATE};

    public SqlType type();

    String value();
}
