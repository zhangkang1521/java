package org.zk.puzzle.character;

/**
 * Created by Administrator on 10/4/2016.
 */
public class ArrayPrint {
    public static void main(String[] args) {
        String letters = "ABC";
        char[] numbers = {'1', '2', '3'};
        System.out.println(numbers.getClass().getName());
        System.out.println(Integer.toHexString(numbers.hashCode()));
        System.out.println(String.valueOf(numbers));
        System.out.println(numbers);
        System.out.println(letters + " easy as " + numbers);
    }
}
