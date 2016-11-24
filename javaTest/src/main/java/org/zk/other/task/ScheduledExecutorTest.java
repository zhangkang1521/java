package org.zk.other.task;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 11/8/2016.
 */
public class ScheduledExecutorTest {
    public static void main(String[] args) {
        ScheduledExecutorService se = Executors.newScheduledThreadPool(2);

        System.out.println(new Date());
        se.schedule(new Runnable() {
            public void run() {
                System.out.println("5=====" + new Date());
                System.out.println(Thread.currentThread());
                throw new RuntimeException("ss");
//                try {
//                    Thread.sleep(20000);
//                }catch (Exception e) {
//                    // noting
//                }
            }
        }, 5, TimeUnit.SECONDS);

        se.schedule(new Runnable() {
            public void run() {
                System.out.println("10=====" + new Date());
                System.out.println(Thread.currentThread());
                //Thread.sleep(20000);
            }
        }, 5, TimeUnit.SECONDS);
    }
}
