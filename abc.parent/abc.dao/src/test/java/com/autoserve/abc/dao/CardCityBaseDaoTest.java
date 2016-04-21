package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.intf.CardCityBaseDao;

public class CardCityBaseDaoTest extends BaseDaoTest {

    @Resource
    private CardCityBaseDao cardCityBaseDao;

    @Test
    public void test() {
        this.cardCityBaseDao.selectByPrimaryKey(1);
    }
}
