package org.zk.puzzle.character;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 10/5/2016.
 */
public class StringReplace2 {
    public static void main(String[] args) {
        String str = "org.zk.puzzle.charset";
        System.out.println(File.separator);
        //System.out.println("org\zk\puzzle\charset");
        //System.out.println(str.replaceAll(Pattern.quote("."), File.separator));
        System.out.println(Matcher.quoteReplacement(File.separator));
        System.out.println(str.replaceAll(Pattern.quote("."), Matcher.quoteReplacement(File.separator)));
        System.out.println(str.replace(".", File.separator));
        System.out.println(str.replace('.', File.separatorChar));
    }
}
