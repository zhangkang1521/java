/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;

/**
 * 类TransferTest.java的实现描述：划转测试
 * 
 * @author J.YL 2014年12月23日 下午4:00:23
 */
public class TransferTest extends BaseServiceTest {
    @Resource
    private DealRecordService    dealRecord;
    @Resource
    private Callback<DealNotify> moneyTransferedCallback;

    @Test
    public void transferTest() {
        Deal deal = new Deal();
        deal.setBusinessType(DealType.TRANSFER);
        DealDetail detail = new DealDetail();
        DealDetail detail1 = new DealDetail();
        List<DealDetail> details = new ArrayList<DealDetail>();
        deal.setDealDetail(details);
        details.add(detail);
        details.add(detail1);

        detail.setDealDetailType(DealDetailType.APPROPRIATE_MONEY);
        detail.setMoneyAmount(BigDecimal.valueOf(10));
        detail.setPayAccountId("13888888888");
        detail.setReceiveAccountId("15209855822");

        detail1.setDealDetailType(DealDetailType.INSURANCE_FEE);
        detail1.setMoneyAmount(BigDecimal.valueOf(10));
        detail1.setPayAccountId("13260714968");
        detail1.setReceiveAccountId("15209855822");

        InnerSeqNo seq = InnerSeqNo.getInstance();
        deal.setInnerSeqNo(seq);
        deal.setOperator(1);
        deal.setBusinessId(1);
        dealRecord.createBusinessRecord(deal, moneyTransferedCallback);
        dealRecord.invokePayment(seq.getUniqueNo());
    }
}
