package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserContactDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.result.BaseResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 10:11.
 */
public class UserContactServiceTest extends BaseServiceTest {
    @Resource
    private UserContactService userContactService;

    @Test
    public void testModify(){
        UserContactDO userContactDO = new UserContactDO();
        userContactDO.setUcId(1);
        userContactDO.setUcAddress("测试地址");
        BaseResult result =  userContactService.modifyUserContact(userContactDO);
        Assert.assertEquals(result.isSuccess(), true);
    }
}
