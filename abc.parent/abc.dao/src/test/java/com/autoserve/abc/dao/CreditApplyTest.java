package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.CreditApplyDO;
import com.autoserve.abc.dao.intf.CreditApplyDao;

/**
 * 类CreditApplyTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月3日 上午10:26:41
 */
public class CreditApplyTest extends BaseDaoTest {

    @Resource
    private CreditApplyDao CreditApplyDao;

    @Test
    public void test() {

        CreditApplyDO creditApplyDO = new CreditApplyDO();
        creditApplyDO.setCreditApplyAmount(BigDecimal.ZERO);
        creditApplyDO.setCreditApplyDate(new Date());
        creditApplyDO.setCreditType(0);
        int a = this.CreditApplyDao.insert(creditApplyDO);
        System.out.println(a);
    }
}
