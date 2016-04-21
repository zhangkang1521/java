package com.autoserve.abc.service.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-18,11:19
 */
@ContextConfiguration(locations = {"/spring/abc-service-test.xml"})
public class BaseServiceTest extends AbstractTestNGSpringContextTests {
    static final Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);

}
