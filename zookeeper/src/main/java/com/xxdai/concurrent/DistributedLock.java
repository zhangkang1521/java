/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 *
 */
package com.xxdai.concurrent;

import com.xxdai.spring.SpringFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @version $Id: DistributedLock.java 20749 2015-06-29 09:07:14Z qiansenyi $
 * @since jdk1.6
 */
public class DistributedLock {
    private static Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    public final static String ROOT_LOCK = "/locks";//根
    private String lockName;//竞争资源的标志

    private InterProcessMutex mutex;
    private boolean isAcquire;
    private CuratorFramework zkClient;
    //是否一次锁
    private boolean isOnceLock;

    /**
     * @param lockName 竞争资源标志,lockName中不能包含单词lock
     */
    public DistributedLock(String lockName) {
        this.lockName = ROOT_LOCK + "/" + lockName;
        logger.info(String.format("initial DistributedLock [locakName:%s]",this.lockName));
        try {
            zkClient = SpringFactory.getBean("zkFactory");
            mutex = new InterProcessMutex(zkClient, this.lockName);
            isAcquire = false;
            isOnceLock = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lock() {
        try {
            logger.info(String.format("lock DistributedLock [locakName:%s]",lockName));
            isOnceLock = false;
            mutex.acquire();
            isAcquire = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean tryLock() {
        try {
            logger.info(String.format("tryLock DistributedLock [locakName:%s]",lockName));
            isOnceLock = true;
            isAcquire = mutex.acquire(0, TimeUnit.MILLISECONDS);
            return isAcquire;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void unlock() {
        logger.info(String.format("unlock DistributedLock [locakName:%s,isAcquire:%b]",lockName,isAcquire));
        if (!isAcquire)
            return;
        try {
            logger.info("unlock: " + this.lockName);
            mutex.release();
            if (isOnceLock) {
                zkClient.delete().inBackground().forPath(this.lockName);
                logger.info("delete lock: " + this.lockName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
