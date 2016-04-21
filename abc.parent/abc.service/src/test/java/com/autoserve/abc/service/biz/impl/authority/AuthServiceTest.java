package com.autoserve.abc.service.biz.impl.authority;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * Created by pp on 2014/12/12.
 */
public class AuthServiceTest extends BaseServiceTest {

    @Resource
    private AuthorityService authorityService;

    @Test
    public void testA(){
        System.err.println(JSON.toJSONString(authorityService.queryMenuByEmpId(27)));
    }
}
