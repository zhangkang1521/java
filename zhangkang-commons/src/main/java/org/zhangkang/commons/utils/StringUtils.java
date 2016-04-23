package org.zhangkang.commons.utils;

/**
 * String工具类，继承自org.apache.commons.lang3.StringUtils<br>
 * Created by Administrator on 2016/4/23.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    /**
     * 根据属性生成set方法的名字
     * @param prop
     * @return
     */
    public static String buildSetMethodName(String prop){
        if(isBlank(prop)){
            throw new IllegalArgumentException("参数不合法");
        }
        //TODO 首字母-32实现，看性能
        return "set"+prop.substring(0, 1).toUpperCase()+prop.substring(1);
    }
}
