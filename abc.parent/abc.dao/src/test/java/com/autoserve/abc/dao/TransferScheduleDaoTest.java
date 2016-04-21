package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.intf.TransferScheduleDao;

/**
 * 类TransferScheduleDaoTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年1月6日 下午4:32:19
 */
public class TransferScheduleDaoTest extends BaseDaoTest {
    @Resource
    private TransferScheduleDao transferScheduleDao;

    @Test
    public void testSelect() {
        PageCondition pageCondition = new PageCondition();
        int a = this.transferScheduleDao.countSelectByLoanId(1, pageCondition);
        System.out.println(a);
    }
}
