package org.zk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/4/23.
 */
public class LogTest {

    private static Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void test1(){
        logger.trace("xx");
        System.out.println(logger.getClass());
    }
}
