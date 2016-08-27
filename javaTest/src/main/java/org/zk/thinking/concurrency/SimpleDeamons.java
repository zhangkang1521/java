package org.zk.thinking.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 普通线程：JVM必须等待他退出才可以正常退出
 * 后台线程；JVM不需要等待他退出，main线程结束，即退出
 * Created by Administrator on 8/14/2016.
 */
public class SimpleDeamons implements Runnable {
    public void run() {
        try{
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        for(int i=0; i<10; i++){
            Thread thread = new Thread(new SimpleDeamons());
            thread.setDaemon(true);//必须在线程启动前设为后台线程
            thread.start();
        }
        System.out.println("all deamon started");
        TimeUnit.MILLISECONDS.sleep(500);  // 可以改变时间观察效果
    }
}
