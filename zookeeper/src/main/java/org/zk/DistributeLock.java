package org.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by root on 7/30/16.
 */
public class DistributeLock {

    private static final String ADDR = "localhost:2181";
    private final String ROOT = "root"; //锁目录
    private CuratorFramework zkClient;
    private InterProcessMutex mutex;
    private String lockName;

    public DistributeLock(String id){
        this.lockName = ROOT + id;
        try {
            zkClient = createSimple(ADDR);
            mutex = new InterProcessMutex(zkClient, this.lockName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static CuratorFramework createSimple(String connectionString) {
        // these are reasonable arguments for the ExponentialBackoffRetry.
        // The first retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }
}
