package com.autoserve.abc.service.message.sendmail;

import javax.annotation.Resource;



import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.message.mail.SendMailService;

public class SendMailTest extends BaseServiceTest {

    @Resource
    private SendMailService mailSenderService;

    @Test
    public void testSendMail() {
        boolean isSendMail = this.mailSenderService.sendYzm2Mail("787161405@qq.com", "hello", "@@@");
        if (isSendMail) {
            System.out.println("发送成功");
        }
    }
}
