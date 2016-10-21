package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/5/2016.
 */
public class DoubleTest {
    public static void main(String[] args) {
        System.out.println(2.0 - 1.1); // 1.1 无法用double准确表示
        System.out.println(Long.toHexString(Double.doubleToRawLongBits(38414.4)));
        System.out.println(Integer.toHexString(Float.floatToIntBits(2.125f)));
        System.out.println(Integer.toHexString(Float.floatToIntBits(4.125f)));
        System.out.println(Integer.toHexString(Float.floatToIntBits(8.125f)));
    }
}
