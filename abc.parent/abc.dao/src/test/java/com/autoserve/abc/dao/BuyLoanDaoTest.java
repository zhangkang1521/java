package com.autoserve.abc.dao;

import java.util.Arrays;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.intf.BuyLoanDao;

/**
 * 类BaseTest.java的实现描述：TODO 类实现描述
 *
 * @author J.YL 2014年11月13日 上午11:17:47
 */
public class BuyLoanDaoTest extends BaseDaoTest {

    @Resource
    private BuyLoanDao buyLoanDao;

    @Test
    public void testFindByList() {

        System.out.println(buyLoanDao.findByList(Arrays.asList(1, 2)));

    }
}
