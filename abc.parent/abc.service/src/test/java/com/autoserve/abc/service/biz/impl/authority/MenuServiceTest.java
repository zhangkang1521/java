package com.autoserve.abc.service.biz.impl.authority;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.entity.MenuItem;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class MenuServiceTest extends BaseServiceTest {
    static final Logger      logger = LoggerFactory.getLogger(MenuServiceTest.class);

    @Resource
    private MenuService      menuService;

    @Resource
    private AuthorityService authorityService;

    @Test
    public void testMenuInit() {
        PlainResult<MenuItem> root = menuService.queryAllMenu();
        System.err.println(JSON.toJSONString(root));
    }
}
