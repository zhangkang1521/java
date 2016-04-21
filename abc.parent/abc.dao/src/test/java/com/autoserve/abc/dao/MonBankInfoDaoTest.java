package com.autoserve.abc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.dao.intf.MonBankInfoDao;

/**
 * 类MonBankInfoDaoTest.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午2:22:29
 */
public class MonBankInfoDaoTest extends BaseDaoTest {
    @Resource
    private MonBankInfoDao monBankInfoDao;

    // @Test
    public void testInsert() {
        MonBankInfoDO monBankInfoDO = new MonBankInfoDO();
        monBankInfoDO.setMonBankId(1);
        monBankInfoDO.setFunFundName("奥拓基金");
        monBankInfoDO.setMonBankCard("622145201984512354");
        monBankInfoDO.setMonBankName("工商银行");
        monBankInfoDO.setMonFundId(2);
        monBankInfoDO.setMonUserNamer("邓敬雨");
        monBankInfoDao.insert(monBankInfoDO);
        assert monBankInfoDO.getMonBankId() != null;
        System.out.println("!!!!!" + monBankInfoDO.getMonBankId());
    }

    @Test
    public void testUpdate() {
        MonBankInfoDO monBankInfoDO = new MonBankInfoDO();
        monBankInfoDO.setMonBankId(2);
        monBankInfoDO.setFunFundName("雪姣基金");
        monBankInfoDO.setMonBankCard("622145201784512354");
        monBankInfoDO.setMonBankName("中国银行");
        monBankInfoDO.setMonFundId(1);
        monBankInfoDO.setMonUserNamer("celt");
        monBankInfoDao.update(monBankInfoDO);
        System.out.println("!!!!!" + monBankInfoDO.getMonBankId());
    }

    //  @Test
    public void testDelete() {
        monBankInfoDao.delete(2);
    }

    // @Test
    public void testFindById() {
        monBankInfoDao.findById(1);
    }

    @Test
    public void testFindByParam() {
        PageCondition pageCondition = new PageCondition();
        MonBankInfoDO monBankInfoDO = new MonBankInfoDO();
        List<MonBankInfoDO> folist = monBankInfoDao.findListByParam(monBankInfoDO, pageCondition);
        for (MonBankInfoDO monBankInfo : folist) {
            System.out.println("^^^^^^^^" + monBankInfo.getFunFundName());
        }
    }
}
