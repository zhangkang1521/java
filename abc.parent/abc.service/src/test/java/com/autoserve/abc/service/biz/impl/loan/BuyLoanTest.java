/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 类BuyLoanTest.java的实现描述：创建收购标
 *
 * @author J.YL 2014年12月28日 下午11:53:24
 */
public class BuyLoanTest extends BaseServiceTest {

    @Resource
    private BuyLoanService buyLoanService;

    @Test
    public void createBuyLoanTest() {

        BuyLoan pojo = new BuyLoan();

        pojo.setUserId(4);
        pojo.setOriginId(122);
        pojo.setBuyMoney(BigDecimal.valueOf(4000));

        BaseResult baseResult = buyLoanService.createBuyLoan(pojo);
        System.err.println(baseResult.getMessage());
    }
}
