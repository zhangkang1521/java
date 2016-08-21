package org.zk.puzzle.classy;

/**
 * Created by Administrator on 8/20/2016.
 */


public class NotYourType {

    public static void main(String[] args) {
        String s = null;
        System.out.println(s instanceof String); // false
        // 程序中经常写 if(s instanceof String) s.xxx() 返回false 就很安全了

    }
}
