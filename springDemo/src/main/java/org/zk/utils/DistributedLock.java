package org.zk.utils;

/**
 * Created by zhangkang on 2016/5/19.
 */
public class DistributedLock {

    public void lock(){
        SpringFactory.getBean("userDao");
    }
}
