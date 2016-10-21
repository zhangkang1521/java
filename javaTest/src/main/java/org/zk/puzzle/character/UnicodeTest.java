package org.zk.puzzle.character;

/**
 * ah&eacute
 * \u00e9
 * Created by Administrator on 10/4/2016.
 */
public class UnicodeTest {
    public static void main(String[] args) {
        // /u0022 会替换成双引号
        System.out.println("a\u0022.length() + \u0022b".length());
        char c = 0x000A;
        System.out.println(c);
    }
}
