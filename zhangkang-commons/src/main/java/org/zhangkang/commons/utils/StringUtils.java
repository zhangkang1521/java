package org.zhangkang.commons.utils;

import org.zhangkang.commons.annotions.ParamIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * String工具类，继承自org.apache.commons.lang3.StringUtils<br>
 * Created by Administrator on 2016/4/23.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 根据属性生成set方法的名字
     *
     * @param prop
     * @return
     */
    public static String buildSetMethodName(String prop) {
        if (isBlank(prop)) {
            throw new IllegalArgumentException("参数不合法");
        }
        return "set" + first2Upcase(prop);
    }

    /**
     * 首字母大写
     *
     * @param prop
     * @return
     */
    public static String first2Upcase(String prop) {
        if(isEmpty(prop)){
            throw new IllegalArgumentException("参数不合法");
        }
        char[] arr = prop.toCharArray();
        if (arr[0] >= 'a' && arr[0] <= 'z') {
            arr[0] -= 32; // 'a'=97 'A'=65
        }
        return new String(arr);
    }

    /**
     * 根据属性生成get方法的名字
     *
     * @param prop
     * @return
     */
    public static String buildGetMethodName(String prop) {
        if (isBlank(prop)) {
            throw new IllegalArgumentException("参数不合法");
        }
        return "get" + first2Upcase(prop);
    }

    /**
     * 将对象拼装成url参数字符串，即key1=value1&key2=value2&key3=value3
     *
     * @param obj
     * @return
     */
    public static String buildUrlparam(Object obj) {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            ParamIgnore ignore = field.getAnnotation(ParamIgnore.class);
            if (ignore != null) {
                continue;
            }
            String fieldName = field.getName();
            try {
                Method method = cls.getMethod(StringUtils.buildGetMethodName(fieldName));
                Object value = method.invoke(obj);
                sb.append("&");
                sb.append(fieldName);
                sb.append("=");
                if (value == null)
                    sb.append("");
                else
                    sb.append(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.substring(1);
    }
}
