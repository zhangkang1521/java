
package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.FundProfitDO;
import com.autoserve.abc.dao.intf.FundProfitDao;

/**
 * 类FundProfitDaoTest.java的实现描述：TODO 类实现描述
 *
 * @author wangyongheng 2014/12/03
 */
public class FundProfitDaoTest extends BaseDaoTest {

    @Resource
    private FundProfitDao fundProfitDao;

    @Test
    public void testInsert() {
    	FundProfitDO fundProfitDO = new FundProfitDO();
    	//fundProfitDO.setFpProfitId(1);
    	fundProfitDO.setFpFundId(2);
    	fundProfitDO.setFpMinMoney(new BigDecimal(50230));
    	fundProfitDO.setFpMaxMoney(new BigDecimal(0512.10));
    	fundProfitDO.setFpProfitYields(new BigDecimal(53.33));

    	fundProfitDao.insert(fundProfitDO);
        assert fundProfitDO.getFpProfitId() != null;
        System.out.println("!!!!!!!!!!!!!!" + fundProfitDO.getFpProfitId());
    }

    @Test
    public void testDelete() {
    	fundProfitDao.delete(7);
    }

    @Test
    public void testFindById() {
    	FundProfitDO fundProfitDO = new FundProfitDO();
    	fundProfitDO = fundProfitDao.findById(8);
    	System.out.println(fundProfitDO.getFpProfitYields()+"^^^^^^^^^^^^^^^^^^^^");
    	
    }

    @Test
    public void testUpdate() {
    	FundProfitDO fundProfitDO = new FundProfitDO();
    	fundProfitDO.setFpProfitId(6);
    	fundProfitDO.setFpFundId(4);
    	fundProfitDO.setFpMinMoney(new BigDecimal(325.25));
    	fundProfitDO.setFpMaxMoney(new BigDecimal(4.21));
    	fundProfitDO.setFpProfitYields(new BigDecimal(535.10));

    	fundProfitDao.update(fundProfitDO);
        System.out.println(fundProfitDO.getFpMinMoney() + "  !!!!!!!!");
    }

    @Test
    public void testQueryListByParam() {
    	FundProfitDO fundProfitDO = new FundProfitDO();
    	fundProfitDO.setFpFundId(2);
    	List<FundProfitDO> list = fundProfitDao.queryListByParam(fundProfitDO);
        for (FundProfitDO fundProfitDO1 : list) {
        	System.out.println("!!!!!!!!!!!!!!" + fundProfitDO1.getFpProfitId());
		}
    }
    
}
