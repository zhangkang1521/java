package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.RedDO;
import com.autoserve.abc.dao.intf.RedDao;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类RedTest.java的实现描述：红包接口测试
 * 
 * @author fangrui 2014年12月26日 下午7:11:10
 */
public class RedDaoTest extends BaseDaoTest {
    @Resource
    private RedDao redDao;

    // @Test
    public void testInsert() {
        RedDO redDO = new RedDO();
        redDO.setRedName("YY红包");
        redDO.setRedState(1);
        redDO.setRedType(1);
        redDO.setRedAmount(200.00);
        redDO.setRedCreatetime(new Date());
        redDO.setRedCreator(2);
        redDO.setRedDesc("ddd红包的");

        //int returnVal = redDao.insertSelective(redDO);
        int returnVal = redDao.insert(redDO);
        System.out.println("~~~~~~~~~~~~" + returnVal);
    }

    // @Test
    public void testUpdate() {
        RedDO redDO = new RedDO();
        redDO.setRedId(3);
        redDO.setRedName("圣诞红包");
        redDO.setRedState(1);
        redDO.setRedType(1);
        redDO.setRedAmount(200.00);
        redDO.setRedCreatetime(new Date());
        redDO.setRedCreator(2);
        redDO.setRedDesc("ddd红包的");

        int returnVal = redDao.update(redDO);
        System.out.println("~~~~~~~~~~~~" + returnVal);
    }

    // @Test
    public void testFindById() {
        RedDO redDO = redDao.findById(1);
        System.out.println("~~~~~~~~~~~~" + redDO.getRedCreator());
    }

    @Test
    public void testBatchInsertRed() {

        List<RedDO> list = new ArrayList<RedDO>();
        for (int i = 1; i < 5; i++) {
            RedDO redDO = new RedDO();
            redDO.setRedName("测试项目红包");
            redDO.setRedState(1);
            redDO.setRedType(1);
            redDO.setRedAmount(200.00);
            redDO.setRedCreatetime(new Date());
            redDO.setRedCreator(2);
            redDO.setRedDesc("ddd红包的");
            redDO.setRedBizid(i);

            list.add(redDO);
        }
        int count = redDao.batchInsert(list);
        System.out.println("|||||||||||||||||||||||||||||" + count);
    }
}
