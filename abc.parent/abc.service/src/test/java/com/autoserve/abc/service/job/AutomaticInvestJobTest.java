package com.autoserve.abc.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;

/**
 * 类AutomaticInvestJobTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月11日 下午1:22:12
 */
public class AutomaticInvestJobTest extends BaseServiceTest {
    @Autowired
    private AutomaticInvestJob automaticInvestJob;

    @Test
    public void test() {
        automaticInvestJob.run();
    }
}
