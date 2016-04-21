package com.autoserve.abc.service.biz.impl.message;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;

public class SysMessageReplyServiceTest extends BaseServiceTest {

    @Resource
    private SysMessageReplyService sysMessageReplyService;

    @Test
    public void createTest() {
        SysMessageReplyDO sysMessageReplyDO = new SysMessageReplyDO();
        sysMessageReplyDO.setSysMessageId(1);
        sysMessageReplyDO.setSysUserId(3);
        sysMessageReplyDO.setSysReplyContent("这是内容");
        System.out.println(sysMessageReplyService);
        //PageCondition pageCondition = new PageCondition();
        //BaseResult  result = this.sysMessageReplyService.queryByUserId(3, pageCondition);

    }

}
