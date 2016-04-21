package com.autoserve.abc.service.biz.impl.invite;

import com.alibaba.druid.util.Base64;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author RJQ 2015/1/4 18:39.
 */
public class InviteServiceTest extends BaseServiceTest {
    @Autowired
    private InviteService inviteService;

    @Test
    public void testGenerateInviteUrl() throws Exception {
        String url = inviteService.generateInviteUrl(27, InviteUserType.PARTNER).getData();//"27,2" ---> ":jcs:g=="
        System.out.println(url);
    }

    @Test
    public void testDecode() throws Exception{
        String invitationId = new String(Base64.altBase64ToByteArray(":jcs:g=="), "UTF-8");//":jcs:g==" ---> "27,2"
        System.out.println(invitationId);
    }

    @Test
    public void testQueryList(){
        InviteJDO inviteJDO = new InviteJDO();
//        inviteJDO.setInviteeName("季胖胖");
        inviteJDO.setUserEmailIsbinded(1);
        PageResult<InviteJDO> result = inviteService.queryList(inviteJDO, new PageCondition());
        System.out.println(result.getDataSize());
    }
}
