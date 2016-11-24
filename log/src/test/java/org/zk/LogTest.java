package org.zk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/4/23.
 */
public class LogTest {

        private static Logger logger = LoggerFactory.getLogger("aa");
    //private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LogTest.class);

    @Test
    public void test1() {
        try{
            throw new RuntimeException("xx");
        }catch (Exception e){
            logger.error("error happend", e);
        }
    }

    @Test
    public void testCc() {
        new SubClass().b();
    }
}
