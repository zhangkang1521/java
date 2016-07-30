package org.zk;

import org.junit.Test;

/**
 * Created by root on 7/30/16.
 */
public class DistributeLockTest {

    @Test
    public void test1(){
        TestingServer server = new TestingServer();
        DistributeLock lock = new DistributeLock("1");

    }
}
