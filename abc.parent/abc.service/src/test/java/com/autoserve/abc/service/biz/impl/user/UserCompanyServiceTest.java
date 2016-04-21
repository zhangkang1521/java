package com.autoserve.abc.service.biz.impl.user;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * @author RJQ 2014/12/26 10:11.
 */
public class UserCompanyServiceTest extends BaseServiceTest {
    @Resource
    private UserEducationService userCompanyService;

    @Test
    public void testModify(){
    	UserEducation userEducation = new UserEducation();
    	userEducation.setUeId(11);
    	userEducation.setUeMajor("荣誉会长~");
        BaseResult result =  userCompanyService.createUserEducation(userEducation);
        Assert.assertEquals(result.isSuccess(), true);
    }
}
