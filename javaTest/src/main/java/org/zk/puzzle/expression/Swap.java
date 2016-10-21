package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/6/2016.
 */
public class Swap {
    public static void main(String[] args) {
        // long ago, when central processing unit had few registers,
        // it was discovered that one could avoid the use of temporary variable by taking
        // advantage of the property of the exclusive OR operator (^) that (x^y^x) == y
//        int x = 1;
//        int y = 2;
//        x = x ^ y;
//        y = y ^ x;
//        x = y ^ x;
//        System.out.println(x + ", " + y);

        int x = 10;
        int y = 20;
        x ^= y ^= x ^= y;
        // x = x ^ y ^ x ^ y
        // y = y ^ x ^ y
       // x += y += x += y;
        System.out.println(x + ", " + y);

//        int x = 10;
//        int y = 20;
//        y = (x ^= (y ^= x)) ^ y;
//        // 分解成如下3步
//        // tmp1 = 10
//        // tmp2 = 20;
//        // 1.
//        //  x = tmp1
//        //  y(tmp3) = tmp1 ^ tmp2
//        // 2.
//        //  x(tmp4) = tmp1 ^ tmp3
//        //  y = tmp3
//        // 3.
//        //  x = tmp4
//        //  y = tmp4 ^ tmp3
//
//        // x = tmp1 ^ tmp1 ^ tmp2 = tmp2 = 20
//        // y = tmp1 ^ tmp1 ^ tmp2 ^ tmp1 ^ tmp2 = tmp1 = 10
//
//        System.out.println(x + ", " + y);





    }
}
