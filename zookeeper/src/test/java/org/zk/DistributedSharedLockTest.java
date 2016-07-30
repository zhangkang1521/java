package org.zk;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangkang on 2016/5/19.
 */
public class DistributedSharedLockTest {

    Logger logger = LoggerFactory.getLogger(DistributedSharedLockTest.class);


    @Test
    public void test2() throws Exception{
        DistributedSharedLock lock = new DistributedSharedLock("/root");
        lock.acquire();
        lock.release();
    }

    public static void main(String[] args) {
        for(int i=0; i<2; i++){
            new Thread(new MyThread(i)).start();
        }
    }

    static class MyThread implements Runnable {
        int id;
        MyThread(int id){
            this.id = id;
        }
        public void run(){
            DistributedSharedLock lock = new DistributedSharedLock("/a");
            try {
                lock.acquire();
                System.out.println(id+"lock" + new Date());
                Thread.sleep(5000);

                System.out.println(id+"unlock");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(lock!=null) {
                        lock.release();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
}
