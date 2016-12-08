package org.zk.thinking.concurrency;

import java.util.Random;

/**
 * Created by Administrator on 11/24/2016.
 */
public class ThreadLocalTest {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static void main(String[] args) {
//        threadLocal.set(1);
//        System.out.println(threadLocal.get());
//        threadLocal.remove();
//        System.out.println(threadLocal.get());
        threadLocal.set(200);
        new Thread(new A()).start();
        System.out.println(Thread.currentThread()+":"+threadLocal.get());
        new Thread(new A()).start();
        System.out.println(Thread.currentThread()+":"+threadLocal.get());
    }

    static class A implements Runnable {

        ThreadLocal t = new ThreadLocal();

        public void run() {
            int a = new Random().nextInt(100);
            System.out.println(Thread.currentThread()+":"+a);
            threadLocal.set(a);
            System.out.println(Thread.currentThread()+":"+threadLocal.get());
        }
    }
}
