package com.autoserve.abc.service.biz.impl.dealRecord;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.message.mail.SendMailService;

public class DealRecordServiceTest extends BaseServiceTest {
    /*
     * @Resource private InvestService investService;
     * @Resource private DealRecordService dealRecord;
     * @Resource private Callback<DealNotify> investWithdrawedCallback;
     * @Resource private DoubleDryService doubleDryService;
     * @Resource private PayMentService payMentService;
     */
    @Resource
    private SendMailService sendMailService;

    @Test
    public void testQueryList() {
        //sendMailService.sendMailToInvestUser(239, "xyz");
        sendMailService.sendMailToCreditoUser(3, "皖20150403字第0004号");
    }

}
