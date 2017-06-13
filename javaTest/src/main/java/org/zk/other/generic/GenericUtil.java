package org.zk.other.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 得到泛型的 T.class
 * http://blog.csdn.net/gengv/article/details/5178055
 * Created by zhangkang on 2017/6/13.
 */
public class GenericUtil {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Type type = userDao.getClass().getGenericSuperclass();
        System.out.println(type);
        Type[] types = ((ParameterizedType)type).getActualTypeArguments();
        System.out.println(types[0]);

    }
}
