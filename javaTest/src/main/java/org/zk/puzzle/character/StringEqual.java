package org.zk.puzzle.character;

/**
 * Created by Administrator on 10/4/2016.
 */
public class StringEqual {
    public static void main(String[] args) {
        final String pig = "length: 10";
        final String dog = "length: " + pig.length();
        // 结合性
        //System.out.println("Animal are equal: " + pig == dog);
        System.out.println("Animal are equal: " + (pig == dog));
        System.out.println(System.identityHashCode(pig));
        System.out.println(System.identityHashCode(dog));
    }
}
