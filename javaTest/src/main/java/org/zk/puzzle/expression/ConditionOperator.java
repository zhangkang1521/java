package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/6/2016.
 */
public class ConditionOperator {

    // 尽量使两边类型一致
    public static void main(String[] args) {
        char x = 'a';
        int i = 0;
        // 一个操作数是T类型，byte,short,char,另一个操作数是int，常量，而且可以表示成T，返回T
        System.out.println(true ? x : 0); // char
        System.out.println(false ? x : 65); // char
        System.out.println(false ? x : 65536); // int

        System.out.println(false ? i : x);
    }
}
