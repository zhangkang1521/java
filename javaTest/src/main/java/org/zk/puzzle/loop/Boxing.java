package org.zk.puzzle.loop;

/**
 * Created by Administrator on 10/2/2016.
 */
public class Boxing {
    public static void testCache() {
        // -128 ~ 127 有缓冲池 , new Integer永远是新地址
//        Integer a = new Integer(1);
//        Integer b = new Integer(1);
        Integer a = 127;
        Integer b = 127;
//        Integer a = 128;
//        Integer b = 128;
        System.out.println(a == b);
    }

    public static void main(String[] args) {
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        // <= >= 比的是值，!= 比的是地址
        System.out.println(a <= b && a >= b && a != b);
        // 在自动装箱拆箱出现前，返回false，必须兼容以前的程序输出false，所以==永远比较地址
        System.out.println(new Integer(1) == new Integer(1));
        System.out.println(new Integer(1) == 1);
    }
}
