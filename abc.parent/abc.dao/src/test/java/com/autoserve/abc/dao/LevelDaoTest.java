package com.autoserve.abc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.dao.dataobject.search.LoginLogSearchDO;
import com.autoserve.abc.dao.intf.LoginLogDao;

public class LevelDaoTest extends BaseDaoTest {
    @Autowired
    private LoginLogDao loginLogDao;

    @Test
    public void testFindList() {

        LoginLogSearchDO searchDO = new LoginLogSearchDO();
        searchDO.setEmployeeName("rjq");

        List<LoginLogJDO> result = loginLogDao.findListBySearchDO(searchDO, PageCondition.DEFAULT);

        System.err.println(result);
    }

}
