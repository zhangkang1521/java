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

import com.autoserve.abc.dao.dataobject.DealRecordDO;
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
 * 类DealRecordServiceTest.java的实现描述：交易记录表测试
 * 
 * @author J.YL 2014年12月15日 上午9:39:18
 */
//@Test
public class DealRecordServiceTest extends BaseServiceTest {
    @Resource
    private DealRecordService    dealRecord;
    @Resource
    private Callback<DealNotify> investPaidCallback;

    // @Test
    public void modifyDealRecordStateTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new myThread());
            t1.start();
        }
        Thread.sleep((long) 5000.00);
    }

    class myThread extends Thread {
        @Override
        public void run() {
            System.out.println("aaa");
            dealRecord.modifyDealRecordState("a19321c7-1e71-436c-b2f6-4447bb5d1664", "TRADE_FINISHED",
                    BigDecimal.valueOf(0.01));
        }
    }

    //@Test
    public void investTest() {
        Deal deal = new Deal();
        deal.setBusinessType(DealType.INVESTER);

        DealDetail detail = new DealDetail();
        List<DealDetail> details = new ArrayList<DealDetail>();
        deal.setDealDetail(details);
        details.add(detail);
        detail.setDealDetailType(DealDetailType.INVESTE_MONEY);
        detail.setMoneyAmount(BigDecimal.valueOf(2));
        detail.setPayAccountId("13260714968");
        detail.setReceiveAccountId(null);
        InnerSeqNo seq = InnerSeqNo.getInstance();
        deal.setInnerSeqNo(seq);
        deal.setOperator(1);
        deal.setBusinessId(1);
        dealRecord.createBusinessRecord(deal, investPaidCallback);
        dealRecord.invokePayment(seq.getUniqueNo());
    }

    @Test
    public void selectTest() {

        DealRecordDO dealRecordDO = new DealRecordDO();
        dealRecordDO.setDrType(1);
        //PageResult<DealRecordDO> result = dealRecord.queryDealByParams(dealRecordDO, pageCondition);
        //System.out.println(result.getTotalCount());
    }
}
