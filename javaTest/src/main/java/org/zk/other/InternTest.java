package org.zk.other;

import java.io.Serializable;

/**
 * Created by Administrator on 10/14/2016.
 */
public class InternTest {
    public static void main(String[] args) {
        String str1 = new String("abc");
        String str2 = "abc";
        String str3 = str1.intern();
        System.out.println(str1 == str2); // false
        System.out.println(str1 == str3); // false
        System.out.println(str2 == str3); // true
    }
}
