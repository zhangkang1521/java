package com.autoserve.abc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.dao.intf.RealnameAuthDao;

public class RealNameAuthTest extends BaseDaoTest {
    @Resource
    private RealnameAuthDao realNameAuthDao;

    @Test
    public void queryList() {
        RealnameAuthJDO realnameAuthJDO = new RealnameAuthJDO();
        realnameAuthJDO.setUserName("季胖胖");
        List<RealnameAuthJDO> list = realNameAuthDao.findRealnameListByParam(realnameAuthJDO, null);
        System.out.println("=====list ==" + list.size());
    }

}
