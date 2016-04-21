package com.autoserve.abc.service.biz.impl.message.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.message.sms.SendMsgService;

public class SendMsgServiceTest extends BaseServiceTest {
	@Autowired
	private SendMsgService sendMsgService;
	
	@Test
	public void test(){
		boolean flag = sendMsgService.sendMsg("18255137884", "短信测试", "张康", "2");
		System.out.println(flag);
	}
}
