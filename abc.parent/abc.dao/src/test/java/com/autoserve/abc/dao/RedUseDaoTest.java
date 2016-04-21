package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.intf.RedUseDao;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 红包使用接口测试
 * 
 * @author fangrui 2014年12月26日 下午8:44:00
 */
public class RedUseDaoTest extends BaseDaoTest {
    @Resource
    private RedUseDao redUseDao;

    //@Test
    public void testInsert() {
        RedUseDO redUseDO = new RedUseDO();
        redUseDO.setRuType(2);
        redUseDO.setRuBizId(3);
        redUseDO.setRuUserid(4);
        redUseDO.setRuUsetime(new Date());
        redUseDO.setRuDesc("笔记本22");
        redUseDO.setRuAmount(600.00);
        redUseDO.setRuUsecount(7);
        redUseDO.setRuRedvsendId(3);
        redUseDO.setRuRemainderAmount(90.00);
        redUseDO.setRuDeductFee(10.00);
        redUseDO.setRuDeductDiscount(11.00);
        int res = redUseDao.insert(redUseDO);
        System.out.println("~~~~~~~~~~~~~~~~" + res);
    }

    // @Test
    public void testUpdate() {
        RedUseDO redUseDO = new RedUseDO();
        redUseDO.setRuId(4);
        redUseDO.setRuType(1);
        redUseDO.setRuBizId(2);
        redUseDO.setRuUserid(1);
        redUseDO.setRuUsetime(new Date());
        redUseDO.setRuDesc("哈牛逼1");
        redUseDO.setRuAmount(700.00);
        redUseDO.setRuUsecount(8);
        redUseDO.setRuRedvsendId(2);
        redUseDO.setRuRemainderAmount(90.00);
        redUseDO.setRuDeductFee(10.00);
        redUseDO.setRuDeductDiscount(11.00);
        int res = redUseDao.update(redUseDO);
        System.out.println("~~~~~~~~~~~~~~~" + res);
    }

    //@Test
    public void testFindById() {
        System.out.println("~~~~~~~~~~~~~~~~~~~" + redUseDao.findById(2).getRuDesc());

    }

    // @Test
    public void testFindListByParam() {
        List<RedUseDO> list = redUseDao.findListByParam(null, null);
        System.out.println("~~~~~~~~~~~~~~~~~" + list.get(1).getRuDesc());
    }

    @Test
    public void testCountListByParam() {
        int num = redUseDao.countListByParam(null);
        System.out.println("~~~~~~~~~~~~~~~~~" + num);
    }

}
