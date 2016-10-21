package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/6/2016.
 */
public class Cast {
    public static void main(String[] args) {
        // sign extension is performed if the type of the original value is signed
        // zero extension if it is a char, regardless of the type to which it is being converted
        System.out.println((int) (char) (byte) -1);
        char ch = 0xffff;
        char ch2 = 0x0001;
        System.out.println((int) ch);
        System.out.println((int) ch2);
    }
}
