package org.zk.other.string;

import java.lang.reflect.Field;

/**
 * https://mp.weixin.qq.com/s?__biz=MjM5NzMyMjAwMA==&mid=2651477248&idx=2&sn=11dbb31ee97e1685a27266946585128d&scene=0#rd
 * Created by zhangkang on 2017/8/9.
 */
public class StringModify {
    public static void main(String[] str) throws Exception {

        String test = "aaaa";
        String test2 = test;
        String test3 = new String(test);
        String test4 = new String(test.toCharArray());

        System.out.println(System.identityHashCode(test));
        System.out.println(System.identityHashCode(test2));
        System.out.println(System.identityHashCode(test3));
        System.out.println(System.identityHashCode(test4));

        System.out.println(test.hashCode());
        System.out.println(test2.hashCode());
        System.out.println(test3.hashCode());
        System.out.println(test4.hashCode());

        Field values = String.class.getDeclaredField("value");
        values.setAccessible(true);
        char[] ref = (char[]) values.get(test); //返回指定对象上此 Field 表示的字段的值。
        ref[0] = 'b';

        System.out.println("aaaa");
        System.out.println(test);
        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);

    }
}
