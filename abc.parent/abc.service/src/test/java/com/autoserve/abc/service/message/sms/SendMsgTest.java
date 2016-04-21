package com.autoserve.abc.service.message.sms;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;

/**
 * @author wuqiang.du Created on 2014-12-18 15:24:25
 */
public class SendMsgTest extends BaseServiceTest {

    @Resource
    private SendMsgService sendMsgService;

    @Test
    public void testSendMsg() {
	boolean isSendMsg = this.sendMsgService.sendMsg("15656575526", "你好,收到",
		"杜武强","1");
	System.out.println(isSendMsg);
    }
}
