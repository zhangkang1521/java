package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.intf.ChargeRecordDao;

public class ChargeRecordDaoTest extends BaseDaoTest {
    @Resource
    private ChargeRecordDao chargeRecordDao;

    @Test
    public void testFindByID() {
    }
}
