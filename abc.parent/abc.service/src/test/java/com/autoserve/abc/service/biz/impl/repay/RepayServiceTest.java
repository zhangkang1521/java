/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.repay;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.citrus.util.Assert;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 还款测试
 */
public class RepayServiceTest extends BaseServiceTest {

    @Resource
    private RepayService repayService;

    @Test
    public void testRepay() {

        int loanId = 51;
        int repayPlanId = 40;

        BaseResult result = repayService.repay(loanId, repayPlanId, PayType.COMMON_CLEAR, 112);

        System.out.println(result.getMessage());
        Assert.assertTrue(result.isSuccess());
    }
}
