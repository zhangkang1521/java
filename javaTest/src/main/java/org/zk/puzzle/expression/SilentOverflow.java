package org.zk.puzzle.expression;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by Administrator on 10/5/2016.
 */
public class SilentOverflow {
    public static void main(String[] args) {
        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000; // 以int计算，溢出
        System.out.println(MICROS_PER_DAY);
        final long MICROS_PER_DAY2 = 24L * 60 * 60 * 1000 * 1000;
        System.out.println(MICROS_PER_DAY2);
    }
}
