package org.zk.concurrency;

import java.util.concurrent.TimeUnit;

/**
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
            thread.setDaemon(true);
            thread.start();
        }
        System.out.println("all deamon started");
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
