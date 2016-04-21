package com.autoserve.abc.service.biz.impl.cash;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;

/**
 * 类CashInvesterServiceTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年1月28日 下午12:27:09
 */
public class CashInvesterServiceTest extends BaseServiceTest {

    @Resource
    private CashInvesterService CashInvesterService;

    @Test
    public void test() {
        CashInvesterService.queryCashInvester(1);
    }
}
