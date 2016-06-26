package org.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by zhangkang on 2016/5/19.
 */
public class DistributedSharedLock implements Watcher {

    private static final String ADDR = "192.168.129.12:2181";
    private static final String LOCK_NODE = "guid-lock-";
    private String rootLockNode; //锁目录
    private ZooKeeper zk = null;
    private Integer mutex;
    private Integer currentLock;

    /**
     * 构造函数实现
     * 连接zk服务器
     * 创建zk锁目录
     *
     * @param rootLockNode
     */
    public DistributedSharedLock(String rootLockNode) {
        this.rootLockNode = rootLockNode;
        try {
            //连接zk服务器
            zk = new ZooKeeper(ADDR, 10 * 10000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex = new Integer(-1);
        // Create ZK node name
        if (zk != null) {
            try {
                //建立根目录节点
                Stat s = zk.exists(rootLockNode, false);
                if (s == null) {
                    zk.create(rootLockNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                System.out.println("Keeper exception when instantiating queue: " + e.toString());
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception");
            }
        }
    }

    /**
     * 请求zk服务器，获得锁
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void acquire() throws KeeperException, InterruptedException {
        byte[] data = "test".getBytes(); //节点数据
        // 创建锁节点 create -e -s
        String lockName = zk.create(rootLockNode + "/" + LOCK_NODE, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        synchronized (mutex) {
            while (true) {
                // 获得当前锁节点的number，和所有的锁节点比较
                Integer acquireLock = new Integer(lockName.substring(lockName.lastIndexOf('-') + 1));
                List<String> childLockNode = zk.getChildren(rootLockNode, true);

                SortedSet<Integer> sortedLock = new TreeSet<Integer>();
                for (String temp : childLockNode) {
                    Integer tempLockNumber = new Integer(temp.substring(temp.lastIndexOf('-') + 1));
                    sortedLock.add(tempLockNumber);
                }

                currentLock = sortedLock.first();

                //如果当前创建的锁的序号是最小的那么认为这个客户端获得了锁
                if (currentLock >= acquireLock) {
                    System.out.println("thread_name=" + Thread.currentThread().getName() + "|attend lcok|lock_num=" + currentLock);
                    return;
                } else {
                    //没有获得锁则等待下次事件的发生
                    System.out.println("thread_name=" + Thread.currentThread().getName() + "|wait lcok|lock_num=" + currentLock);
                    mutex.wait();
                }
            }
        }
    }


    /**
     * 释放锁
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void release() throws KeeperException, InterruptedException {
        String lockName = String.format("%010d", currentLock);
        zk.delete(rootLockNode + "/" + LOCK_NODE + lockName, -1);
        System.out.println("thread_name=" + Thread.currentThread().getName() + "|release lcok|lock_num=" + currentLock);
    }

    public void close() throws Exception{
        zk.close();
    }

    public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
