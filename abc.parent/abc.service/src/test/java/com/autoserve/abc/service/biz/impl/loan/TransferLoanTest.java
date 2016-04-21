/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 类TransferLoanTest.java的实现描述：创建转让标
 *
 * @author J.YL 2014年12月28日 下午11:40:18
 */
public class TransferLoanTest extends BaseServiceTest {

    @Resource
    private TransferLoanService transferLoanService;

    @Test
    public void createTransferLoanTest() {
        TransferLoan pojo = new TransferLoan();

        pojo.setUserId(1);
        pojo.setOriginId(132);
        pojo.setTransferMoney(BigDecimal.valueOf(400));

        BaseResult baseResult = transferLoanService.createTransferLoan(pojo);
        System.err.println(baseResult.getMessage());
    }
}
