package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

public class RoleServiceTest extends BaseServiceTest {

    @Resource
    private RoleService      roleService;

    @Resource
    private AuthorityService authorityService;

    @Test
    public void testmodifyMatrix() {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(5);
        map.put(1, list);
        BaseResult result = authorityService.modifyRoleMatrix(map, 1);
        System.err.println(JSON.toJSONString(result));
    }

    // @Test
    public void createTest() {
        RoleDO role = new RoleDO();
        role.setRoleName("管2理员");
        role.setRoleSort(2);
        System.err.println(JSON.toJSONString("-------------" + JSON.toJSONString(roleService.createRole(role))));
    }

    //@Test
    public void queryPageTest() {
        PageCondition condition = new PageCondition(1, 2);
        PageResult<RoleDO> result = roleService.queryPageRole(condition);
        System.out.println(JSON.toJSONString(result));
    }

}
