package com.autoserve.abc.dao;

import javax.annotation.Resource;

import com.autoserve.abc.dao.dataobject.RoleBtnDO;
import com.autoserve.abc.dao.intf.RoleBtnDao;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.RoleDao;


public class RoleDaoTest extends BaseDaoTest {

    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleBtnDao roleBtnDao;

    @Test
    public void testBtInsert(){
        RoleBtnDO rdo=new RoleBtnDO();
        rdo.setRoleId(1);
        rdo.setBtnId(1);
        rdo.setMenuId(1);
        int i=roleBtnDao.insert(rdo);
        System.err.println(i);
    }

    @Test
    public void testFindByName(){
        RoleDO rdo=roleDao.findByRoleName("测试---------角色");
        System.err.println(rdo==null);
    }

    @Test
    public void testInsert() {
        RoleDO role = new RoleDO();
        role.setRoleName("测试角色");
        role.setRoleSort(0);
        roleDao.insert(role);
        System.err.println(role == null);

    }

    @Test
    public void testQuery() {
        int i = roleDao.findAllCount();
        System.out.println(i);
    }
}
