package com.autoserve.abc.service.biz.impl.user;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;

public class UserOwnerTest extends BaseServiceTest {
    @Resource
    private UserOwnerService userOwnerService;

    @Test
    public void test() {
        UserOwner UserOwner = new UserOwner();

        UserOwner.setUoAddress("121321");
        this.userOwnerService.createUserOwner(UserOwner);
        System.out.println(UserOwner.getUoId());
    }

}
