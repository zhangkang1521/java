/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.invest;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 类invest.java的实现描述：invest
 * 
 * @author J.YL 2014年12月28日 下午9:17:47
 */
public class InvestTest extends BaseServiceTest {
    @Resource
    private InvestService     investService;
    @Resource
    private DealRecordService dealRecordService;

    //@Test
    public void createInvestCommonTest() {
        Invest inv = new Invest();
        //投资人为userId为1的个人用户
        inv.setUserId(1);
        inv.setInvestMoney(BigDecimal.valueOf(888));
        inv.setBidId(85);
        inv.setOriginId(85);
        inv.setBidType(BidType.COMMON_LOAN);
        inv.setCreatetime(new Date());

        //PlainResult<Integer> investResult = investService.createInvest(inv);
        //System.err.println(investResult.getMessage());

    }

    //@Test
    public void invokeCommonTest() {
        BaseResult invokeResult = dealRecordService.invokePayment("02b4c942-6549-4e8b-8521-68a71b7c1256");
        System.err.println(invokeResult.getMessage());
    }

    //@Test
    public void createInvestTransferTest() {
        Invest inv = new Invest();
        //投资人为userId为1的个人用户
        inv.setUserId(1);
        inv.setInvestMoney(BigDecimal.valueOf(100));
        inv.setBidId(1);
        inv.setOriginId(1);
        inv.setBidType(BidType.TRANSFER_LOAN);
        inv.setCreatetime(new Date());
        //investService.createInvest(inv);
    }

    //@Test
    public void createInvestBuyTest() {
        Invest inv = new Invest();
        //投资人为userId为1的个人用户
        inv.setUserId(1);
        inv.setInvestMoney(BigDecimal.valueOf(100));
        inv.setBidId(1);
        inv.setOriginId(1);
        inv.setBidType(BidType.BUY_LOAN);
        inv.setCreatetime(new Date());
        //investService.createInvest(inv);
    }

}
