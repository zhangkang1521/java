package com.autoserve.abc.service.biz.impl.pdf;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.message.mail.SendMailService;

public class exportPdfServiceTest extends BaseServiceTest{
	@Resource
    private SendMailService       sendMailService;
	
	@Test
	public void pdfTest(){
		sendMailService.sendMailToCreditoUser(18, "皖20150724字第0064号");
	}

}
