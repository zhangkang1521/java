package org.zk.puzzle.classy;

/**
 * Created by Administrator on 9/10/2016.
 */

class Cache {
    static {
        initializeIfNecessary();
    }

    private static int sum;

    public static int getSum() {
        initializeIfNecessary();
        return sum;
    }

    private static boolean initialized = false; // static代码块 执行完后，又初始化为false

    private static void initializeIfNecessary() {
        if (!initialized) {
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            initialized = true;
        }
    }
}

class Cache2 {


    private static final int SUM = computeSum();

    public static int getSum() {
        return SUM;
    }

    private static int computeSum() {
        int result = 0;
        for (int i = 0; i <= 100; i++) {
            result += i;
        }
        return result;
    }
}

public class SumFun {

    public static void main(String[] args) {
        System.out.println(Cache2.getSum());
    }
}
