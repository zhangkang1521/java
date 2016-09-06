package org.zk.puzzle.classy;

/**
 * Created by zhangkang on 2016/9/6.
 */
public class Null {

    public static void f() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        // 不会报错，静态方法只与类有关，与对象无关
        ((Null) null).f();
    }
}
