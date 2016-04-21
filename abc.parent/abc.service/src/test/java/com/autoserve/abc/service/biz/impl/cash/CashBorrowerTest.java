/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;

/**
 * 类CashBorrowerTest.java的实现描述：借款人资金对账测试
 * 
 * @author J.YL 2014年12月18日 下午5:51:43
 */
public class CashBorrowerTest extends BaseServiceTest {
    @Resource
    private CashBorrowerService cashBorrower;

    @Test
    public void test() {
        PageCondition pageCondition = new PageCondition(0, 10);
        System.out.println(JSON.toJSON(cashBorrower.queryCashBorrower(null, pageCondition)));
    }
}
