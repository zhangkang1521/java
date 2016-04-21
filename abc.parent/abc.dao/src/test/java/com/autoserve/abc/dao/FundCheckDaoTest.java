
package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.FundCheckDO;
import com.autoserve.abc.dao.intf.FundCheckDao;

/**
 * 类FundCheckDaoTest.java的实现描述：TODO 类实现描述
 *
 * @author wangyongheng 2014/12/03
 */
public class FundCheckDaoTest extends BaseDaoTest {

    @Resource
    private FundCheckDao fundCheckDao;

    @Test
    public void testInsert() {
    	FundCheckDO fundCheckDO = new FundCheckDO();
    	fundCheckDO.setFcCheckId(1);
    	fundCheckDO.setFcOrderId(2);
    	fundCheckDO.setFcCheckEmp(3);
    	fundCheckDO.setFcCheckDate(new Date());
    	fundCheckDO.setFcCheckIdear("TTTTTTOOOOOOOOOOO");
    	fundCheckDO.setFcRechargeMoney(new BigDecimal(5));
    	//fundCheckDO.setFcRechargeDate(new Date());
    	fundCheckDO.setFcCheckState(1);

    	fundCheckDao.insert(fundCheckDO);
        assert fundCheckDO.getFcCheckId() != null;
        System.out.println("!!!!!!!!!!!!!!" + fundCheckDO.getFcCheckId());
    }

    @Test
    public void testDelete() {
    	fundCheckDao.delete(3);
    }

    @Test
    public void testFindById() {
    	FundCheckDO fundCheckDO = new FundCheckDO();
    	fundCheckDO = fundCheckDao.findById(8);
    	System.out.println(fundCheckDO.getFcCheckIdear()+"^^^^^^^^^^^^^^^^^^^^");
    	
    }

    @Test
    public void testUpdate() {
    	FundCheckDO fundCheckDO = new FundCheckDO();
    	//fundCheckDO.setFcCheckId(9);
    	fundCheckDO.setFcOrderId(4);
    	fundCheckDO.setFcCheckEmp(4);
    	fundCheckDO.setFcCheckDate(new Date());
    	fundCheckDO.setFcCheckIdear("HHHHHHHHHHHHH");
    	fundCheckDO.setFcRechargeMoney(new BigDecimal(100));
    	fundCheckDO.setFcRechargeDate(new Date());
    	fundCheckDO.setFcCheckState(2);
    	
    	fundCheckDao.update(fundCheckDO);
        System.out.println(fundCheckDO.getFcRechargeDate() + "  !!!!!!!!");
    }

}
