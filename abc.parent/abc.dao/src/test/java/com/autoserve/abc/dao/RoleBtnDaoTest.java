package com.autoserve.abc.dao;

import javax.annotation.Resource;

import com.autoserve.abc.dao.dataobject.RoleBtnDO;
import com.autoserve.abc.dao.intf.RoleBtnDao;

public class RoleBtnDaoTest extends BaseDaoTest {

    @Resource
    private RoleBtnDao roleBtnDao;

    //@Test
    public void testFind() {
        RoleBtnDO btn = roleBtnDao.findByMenuIRoleBtn(1, 1, 1);
        System.err.println(btn == null);
    }

}
