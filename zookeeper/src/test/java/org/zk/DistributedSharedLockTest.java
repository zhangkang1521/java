package org.zk;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangkang on 2016/5/19.
 */
public class DistributedSharedLockTest {

    @Test
    public void test() throws Exception{

    }

    @Test
    public void test2() throws Exception{
        for(int i=0; i<5; i++){
            new Thread(new MyThread(i)).start();
        }
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
                lock.release();
                System.out.println(id+"unlock");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
