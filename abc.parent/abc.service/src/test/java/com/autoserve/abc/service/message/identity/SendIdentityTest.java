package com.autoserve.abc.service.message.identity;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;

public class SendIdentityTest extends BaseServiceTest {
    @Resource
    private RealnameAuthService realnameAuthService;

    @Test
    public void testSendMail() {
        realnameAuthService.transmitRealNameAudit(113);

    }
}
