package org.zk.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 8/14/2016.
 */
public class LiftOff implements Runnable{
    protected int countDown = 10;// 默认执行次数
    private static int taskCount = 0;
    private final int id = taskCount++;

    public String status(){
        return "#"+id+"("+countDown+") ";
    }

    public void run() {
        while(countDown-- > 0){
            System.out.println(status());
            // yield 不一定靠谱
//            Thread.yield();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
