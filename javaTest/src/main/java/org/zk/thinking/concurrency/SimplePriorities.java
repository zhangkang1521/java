package org.zk.thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 8/14/2016.
 */
public class SimplePriorities implements Runnable {

    private int countDown = 10;
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority) {
        this.priority = priority;

    }

    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true) {
            for (int i = 1; i < 1000000; i++) {
                d += (Math.PI + Math.E) / (double) i;
//                if (i % 100 == 0) {
//                    Thread.yield();
//                }
            }
            if(--countDown == 0)
                return;
            System.out.println(this);
        }

    }

    @Override
    public String toString() {
        return Thread.currentThread()+" "+countDown;
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++)
            es.execute(new Thread(new SimplePriorities(Thread.MIN_PRIORITY)));
        es.execute(new Thread(new SimplePriorities(Thread.MAX_PRIORITY)));
    }
}
