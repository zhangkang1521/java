package org.zk.puzzle.library;

/**
 * Created by zhangkang on 2016/9/8.
 */
public class OneLine {
    public static void main(String[] args) {
//        System.out.println(Integer.bitCount(5));
//        System.out.println(Integer.decode("1"));
        for(int i=0; i<10; i++) {
           // System.out.println(Integer.lowestOneBit(i));
           // System.out.println(Integer.numberOfLeadingZeros(i));
        }
//        System.out.println(Integer.parseInt("FF", 16));
//        System.out.println(010);
//        System.out.println(0x10);
//        System.out.println(Integer.reverse(-1));
        Integer i = 24484554;
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(Integer.reverseBytes(i)));
    }
}
