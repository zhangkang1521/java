package org.zk.thinking.concurrency;


/**
 * Created by Administrator on 8/14/2016.
 */
public class MainThread {

    public static void main(String[] args) {
        // run方法并没有启动一个线程，只是简单的调用而已
//        LiftOff liftOff = new LiftOff();
//        liftOff.run();
        for(int i=0; i<5; i++) {
            Thread thread = new Thread(new LiftOff());
            thread.start();
        }
        System.out.println("=============");
    }
}
