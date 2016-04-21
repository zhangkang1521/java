/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.intf.IncomePlanDao;

/**
 * 类PlanIncomeTest.java的实现描述：TODO 类实现描述
 * 
 * @author J.YL 2014年12月28日 下午11:11:00
 */
public class PlanIncomeTest extends BaseDaoTest {
    @Resource
    private IncomePlanDao dao;

    //@Test
    public void updateIncomePlanByAllocPunishMoneyTest() {
        dao.updateIncomePlanByAllocPunishMoney(1, 1000, 1);
    }

    @Test
    public void Test() {
        IncomePlanDO incomePlanDO = new IncomePlanDO();
        incomePlanDO.setPiBeneficiary(1);
        IncomePlanDO incomePlan = dao.findlastIncomePlanBySearch(incomePlanDO, null);
        System.out.println(incomePlan.getPiId());
    }

}
