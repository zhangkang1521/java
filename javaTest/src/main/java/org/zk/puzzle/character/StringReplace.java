package org.zk.puzzle.character;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 10/5/2016.
 */
public class StringReplace {
    public static void main(String[] args) {
        String str = "org.zk.puzzle.charset";
        System.out.println(str.replaceAll(".", "/"));
        System.out.println(str.replaceAll("\\.", "/"));
        System.out.println(str.replaceAll(Pattern.quote("."), "/"));
    }
}
