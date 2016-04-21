package com.autoserve.abc.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.intf.InvestDao;

/**
 * InvestDaoTest
 */
public class InvestDaoTest extends BaseDaoTest {

    @Resource
    private InvestDao investDao;

    //@Test
    public void testInsertSearch() {
        InvestDO invest = new InvestDO();

        invest.setInBidId(1);
        invest.setInBidType(1);

        System.out.println(investDao.findListByParam(invest, null));

    }

    @Test
    public void test() {
        InvestSearchJDO InvestSearchJDO = new InvestSearchJDO();
        InvestSearchJDO.setUserId(1);
        InvestSearchJDO.setEndDate(new Date());

        System.out.println(this.investDao.findMyInvestClean(InvestSearchJDO, new PageCondition()).size());
    }
}
