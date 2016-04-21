package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.intf.BankInfoDao;
import com.autoserve.abc.dao.intf.CashInvesterViewDao;

/**
 * 类TestApplication.java的实现描述：TODO 类实现描述
 * 
 * @author J.YL 2014年11月13日 下午6:02:00
 */
public class BankInfoDaoTest extends BaseDaoTest {

    @Resource
    private CashInvesterViewDao cashInvesterViewDao;
    @Resource
    private BankInfoDao         dao;

    @Test
    public void tester() {
        System.out.println(JSON.toJSON(dao.findByUserId(1234)));
    }

    //@Test
    public void testBankInfoInsert() {
        BankInfoDO bi = new BankInfoDO();
        bi.setBankLawer("jyl");
        bi.setBankName("jyl");
        bi.setBankNo("12456778");
        bi.setBankUserId(1234);
        bi.setBankUserType(116);
        dao.insert(bi);
        System.out.println(bi.getBankId());

    }
}
