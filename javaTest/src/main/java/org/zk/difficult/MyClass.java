package org.zk.difficult;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class MyClass {
    private String name;

    public static void main(String[] args) {
        MyClass m1 = new MyClass();
        MyClass m2 = new MyClass();
        m1.name = m2.name = "name";
        callMe(m1, m2);
        System.out.println(m1 + " & " + m2);
    }

    /**
     * java里方法传参都是传递引用的copy
     * @param m
     */
    private static void callMe(MyClass... m) {
        m[0] = m[1];
        m[0].name = "new name";
    }

    @Override
    public String toString() {
        return name;
    }
}
