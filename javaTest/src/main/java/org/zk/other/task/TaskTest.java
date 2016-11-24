package org.zk.other.task;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer缺陷
 * 1.一个任务执行时间过长会影响另一个任务的执行时间，因为只有一个线程
 * 2.一个任务抛出异常，以后的任务不会执行，线程结束
 * Created by Administrator on 11/8/2016.
 */
public class TaskTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.toDate());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("5=====" + new Date());
                    //System.out.println(Thread.currentThread());
                    Thread.sleep(20000);
                   // throw new RuntimeException("xxx");
                }catch (Exception e) {
                    // noting
                }
            }
        }, dateTime.plusSeconds(5).toDate());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("10====="+new Date());
                //System.out.println(Thread.currentThread());
            }
        }, dateTime.plusSeconds(10).toDate());
    }
}
