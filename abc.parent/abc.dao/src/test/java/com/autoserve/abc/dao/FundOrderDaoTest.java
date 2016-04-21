package com.autoserve.abc.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.FundOrderDO;
import com.autoserve.abc.dao.intf.FundOrderDao;

/**
 * 类FundOrderDaoTest.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/03
 */
public class FundOrderDaoTest extends BaseDaoTest {

    @Resource
    private FundOrderDao fundOrderDao;

    @Test
    public void testInsert() {
        FundOrderDO fundOrderDO = new FundOrderDO();
        fundOrderDO.setFoOrderId(1);
        fundOrderDO.setFoFundId(2);
        fundOrderDO.setFoUserId(3);
        fundOrderDO.setFoOrderDate(new Date());
        fundOrderDO.setFoUserName("str4");
        fundOrderDO.setFoUserPhone("str5");
        fundOrderDO.setFoOrderState(6);

        fundOrderDao.insert(fundOrderDO);
        assert fundOrderDO.getFoOrderId() != null;
        System.out.println("!!!!!!!!!!!!!!" + fundOrderDO.getFoOrderId());
    }

    @Test
    public void testDelete() {
        fundOrderDao.delete(3);
    }

    @Test
    public void testFindById() {
        FundOrderDO fundOrderDO = new FundOrderDO();
        fundOrderDO = fundOrderDao.findById(8);
        System.out.println(JSON.toJSON(fundOrderDO) + "^^^^^^^^^^^^^^^^^^^^");

    }

    @Test
    public void testUpdate() {
        FundOrderDO fundOrderDO = new FundOrderDO();
        fundOrderDO.setFoOrderId(8);
        fundOrderDO.setFoFundId(2);
        fundOrderDO.setFoUserId(3);
        fundOrderDO.setFoOrderDate(new Date());
        fundOrderDO.setFoUserName("EEEEEEEE");
        fundOrderDO.setFoUserPhone("RRRRRRRRR");
        fundOrderDO.setFoOrderState(6);

        fundOrderDao.update(fundOrderDO);
        System.out.println(JSON.toJSON(fundOrderDO) + "  !!!!!!!!");
    }

}
