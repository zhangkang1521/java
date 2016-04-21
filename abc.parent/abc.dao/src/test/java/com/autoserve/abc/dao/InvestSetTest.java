package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.InvestSetDO;
import com.autoserve.abc.dao.intf.InvestSetDao;

/**
 * ] 类InvestSetTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月10日 下午4:41:08
 */
public class InvestSetTest extends BaseDaoTest {

    @Resource
    private InvestSetDao investSetDao;

    @Test
    public void testInsert() {

        InvestSetDO investDO = new InvestSetDO();
        investDO.setIsIsOpen(1);
        this.investSetDao.insert(investDO);
    }
}
