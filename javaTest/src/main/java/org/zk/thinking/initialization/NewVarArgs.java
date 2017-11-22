package org.zk.thinking.initialization;

/**
 * Created by Administrator on 9/10/2017.
 */
public class NewVarArgs {
    static void printArgs(int... args) {
        System.out.println(args.getClass());
    }

    /**
     * JVM数组描述符。
     * [Z = boolean
     * [B = byte
     * [S = short
     * [I = int
     * [J = long
     * [F = float
     * [D = double
     * [C = char
     * [L = any non-primitives(Object)
     *
     * @param args
     */
    public static void main(String[] args) {
        printArgs(1);
        printArgs(new Integer(1)); // 转换成原生类型了
        System.out.println(new int[1].getClass());
        System.out.println(new Integer[]{1}.getClass());
    }
}
