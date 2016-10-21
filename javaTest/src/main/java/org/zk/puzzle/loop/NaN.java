package org.zk.puzzle.loop;

/**
 * Created by Administrator on 10/2/2016.
 */
public class NaN {
    public static void main(String[] args) {
        // not a number is a 奇葩，自己都不等于自己
        double a = Double.NaN;
        System.out.println(a == a);
        System.out.println(0.0 / 0.0);
        System.out.println(1.0 / 0.0);
        System.out.println(-1.0 / 0.0);

    }
}
