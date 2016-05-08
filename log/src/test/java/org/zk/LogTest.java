package org.zk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/4/23.
 */
public class LogTest {

    //    private static Logger logger = LoggerFactory.getLogger(LogTest.class);
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LogTest.class);

    @Test
    public void test1() {
        logger.debug("xx");
        System.out.println(logger.getClass());
    }
}
