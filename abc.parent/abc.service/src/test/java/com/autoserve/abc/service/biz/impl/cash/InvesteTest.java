/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;

/**
 * 类InvesteTest.java的实现描述：
 * 
 * @author J.YL 2014年12月23日 下午4:00:04
 */
public class InvesteTest extends BaseServiceTest {
    @Resource
    private InvestService            investService;
    @Resource
    private DealRecordService        dealRecord;
    @Resource
    private Callback<DealNotify>     investWithdrawedCallback;
    @Resource
    //发送请求,获取数据
    private final AbcHttpCallService callService = new AbcHttpCallServiceImpl();

    @Test
    public void withdrawTest() {
        Deal deal = new Deal();
        deal.setBusinessType(DealType.RECHARGE);
        DealDetail detail = new DealDetail();
        List<DealDetail> details = new ArrayList<DealDetail>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("RechargeMoneymoremore", "m21014");
        params.put("OrderNo", "OrderNo");
        params.put("Amount", "10");
        params.put("RechargeType", "");
        params.put("FeeType", "");
        params.put("CardNo", "");
        params.put("RandomTimeStamp", "");

        /*
         * String RechargeMoneymoremore = "m21014"; String PlatformMoneymoremore
         * = "p372"; String OrderNo = "3eaff1a4-edc2-4583-a3b5-c77eea347af4";
         * String Amount = "10"; String RechargeType = ""; String FeeType = "";
         * String CardNo = ""; String RandomTimeStamp = "";
         */
        //String ReturnURL = "http://36.33.24.109:8774/account/myAccount/RechargeReturn";
        //String NotifyURL = "http://36.33.24.109:8774/account/myAccount/RechargeReturn";
        detail.setData(params);

        deal.setDealDetail(details);
        details.add(detail);
        detail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
        detail.setMoneyAmount(BigDecimal.valueOf(10));
        detail.setPayAccountId("13888888888");
        detail.setReceiveAccountId(null);
        InnerSeqNo seq = InnerSeqNo.getInstance();
        deal.setInnerSeqNo(seq);
        deal.setOperator(1);
        deal.setBusinessId(1);
        //PlainResult<DealReturn> result = dealRecord.createBusinessRecord(deal, investWithdrawedCallback);
        //DealReturn sdsd = result.getData();

        //String submitUrl = "http://218.4.234.150:88/main/loan/toloanrecharge.action";
        // Map paramsMap = new HashMap();
        // paramsMap = JSON.parseObject(sdsd.getParams());
        //String resultStr = callService.sendPost(submitUrl, paramsMap).getData();
        //String sda = "";
        //dealRecord.invokePayment(seq.getUniqueNo());
    }
}
