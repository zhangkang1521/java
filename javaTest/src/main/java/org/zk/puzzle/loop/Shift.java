package org.zk.puzzle.loop;

/**
 * Created by Administrator on 9/26/2016.
 */
public class Shift {
    public static void main(String[] args) {
        // <<运算规则：向左移动对应的位数，高位移出，低位的空位补零。
        // >>运算规则： 向右移动对应的位数，低位移出，高位的空位：正数补0，负数补1.
        // >>>运算规则：向右移动对应的位数，低位移出，高位的空位补零

        // 规定实际移动的次数是移动次数和32的余数，也就是移位33次和移位1次得到的结果相同
        int a = -25;
        for(int i=0; i<=33; i++) {
            int b = a << i;
            System.out.printf("%d: %32s %d\n", i, Integer.toBinaryString(b), b);
        }

    }
}
