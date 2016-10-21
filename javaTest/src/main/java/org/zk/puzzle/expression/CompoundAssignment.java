package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/6/2016.
 */
public class CompoundAssignment {
    public static void main(String[] args) {
//        short x = 1;
//        int i = 2;
//        x += i; // E1 = (short)(E1 + E2)
//        //x = x + i; // illegal


        Object x = new Object();
        String i = "i";
        x = x + i;
        //x += i;
    }
}
