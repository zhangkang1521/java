package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 8/14/2016.
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        // 首选创建cachedThreadPool, SingleThread 会顺序化执行任务，一次只有一个线程执行
        ExecutorService es = Executors.newCachedThreadPool();
//        ExecutorService es = Executors.newFixedThreadPool(1);
//        ExecutorService es = Executors.newSingleThreadExecutor();

        for(int i=0; i<5; i++){
            es.execute(new LiftOff());
        }
        es.shutdown();
    }
}
