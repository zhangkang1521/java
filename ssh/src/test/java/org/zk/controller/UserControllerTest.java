package org.zk.controller;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.zk.BaseTest;

/**
 * Created by zhangkang on 2016/5/10.
 */
public class UserControllerTest extends BaseTest{

    private static final Logger logger = Logger.getLogger(UserControllerTest.class);
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    public void test1(){
        logger.debug("log4j-over-slf4j xxd debug");
        logger.warn("xxd warn");
        logger.error("xxd error");
        LOG.debug("xx");
    }
}
