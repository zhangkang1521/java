package org.zk.puzzle.classier;

/**
 * Created by Administrator on 9/15/2016.
 */
public class ShadeOfGray {

    static class Xy extends X.Y {

    }

    public static <T extends X.Y> void access(){
        System.out.println(T.Z);
    }

    public static void main(String[] args) {
        // 变量优先
        System.out.println(X.Y.Z);
        // type is allowed but a variable is not
        // 把 null 转换为 X.Y 肯定是转换成类 static class Y
        System.out.println(((X.Y)null).Z);

        System.out.println(Xy.Z);
        access();
    }
}

class X {
    static class Y {
        static String Z = "Black";
    }

    static C Y = new C();
}

class C {
    String Z = "White";
}
