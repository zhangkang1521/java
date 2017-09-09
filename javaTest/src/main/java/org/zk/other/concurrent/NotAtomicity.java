package org.zk.other.concurrent;

/**
 * https://mp.weixin.qq.com/s?__biz=MzA5MzY4NTQwMA==&mid=2650995323&idx=1&sn=d831411214c588e1044baf2ffc7d23d2&scene=0#rd
 * Created by zhangkang on 2017/8/9.
 */
public class NotAtomicity {
    public static long t = 0;

    public /*synchronized*/ static long getT() {
        return t;
    }

    public /*synchronized*/ static void setT(long t) {
        NotAtomicity.t = t;
    }

    public static class ChangeT implements Runnable {
        private long to;

        public ChangeT(long to) {
            this.to = to;
        }

        public void run() {
            while (true) {
                NotAtomicity.setT(to);
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable {

        public void run() {
            while (true) {
                long tmp = NotAtomicity.getT();
                if (tmp != 100L && tmp != 200L && tmp != -300L && tmp != -400L) {
                    //程序若执行到这里，说明long类型变量t，其数据已经被破坏了
                    System.out.println(tmp);
                }
                Thread.yield();
            }
        }
    }

    // 在32位JVM中，64位的long数据的读和写都不是原子操作，即不具有原子性
    public static void main(String[] args) {
        new Thread(new ChangeT(100L)).start();
        new Thread(new ChangeT(200L)).start();
        new Thread(new ChangeT(-300L)).start();
        new Thread(new ChangeT(-400L)).start();
        new Thread(new ReadT()).start();
        System.out.println("started");
    }
}