package org.zhangkang.commons.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用此注解注解类的属性后，用StringUtils.buildUrlparam(Object)方法将忽略此属性
 * Created by root on 16-6-4.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamIgnore {
}
