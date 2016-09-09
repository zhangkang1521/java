package org.zk.puzzle.library;

import java.util.Arrays;

/**
 * Created by zhangkang on 2016/9/9.
 */
public class Abs {

    private static final int MOD_BASE = 3;

    public static void main(String[] args) {
        // 绝对值返回负数，2147483648溢出了
        System.out.println(Math.abs(Integer.MIN_VALUE));

        int[] arr = new int[3];
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            //arr[Math.abs(i) % MOD_BASE]++;
            int pos = Math.abs(i) % MOD_BASE;
            pos = pos < 0 ? pos + MOD_BASE : pos;
            arr[pos]++;
        }
        System.out.println(Arrays.toString(arr));
    }
}
