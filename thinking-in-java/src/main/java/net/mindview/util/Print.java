package net.mindview.util;

import java.io.*;

/**
 * 输出工具类，可以静态引入使用
 * import static net.mindview.util.Print.*;
 */
public class Print {

    public static void print(Object obj) {
        System.out.println(obj);
    }

    public static void print() {
        System.out.println();
    }

    public static void printnb(Object obj) {
        System.out.print(obj);
    }

    public static PrintStream printf(String format, Object... args) {
        return System.out.printf(format, args);
    }
}
