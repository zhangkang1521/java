package org.zk.puzzle.expression;

/**
 * Created by Administrator on 10/6/2016.
 */
public class Promotion {
    public static void main(String[] args) {
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabe));
        // fix
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabeL));
        System.out.println(Long.toHexString(0xcafebabe));
    }
}
