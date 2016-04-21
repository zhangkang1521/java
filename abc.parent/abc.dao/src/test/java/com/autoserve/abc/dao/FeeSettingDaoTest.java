package com.autoserve.abc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.FeeSettingDO;
import com.autoserve.abc.dao.intf.FeeSettingDao;

/**
 * @author yuqing.zheng Created on 2014-11-26,14:16
 */
public class FeeSettingDaoTest extends BaseDaoTest {
    @Autowired
    private FeeSettingDao       feeSettingDao;

    private final List<Integer> ids = new ArrayList<Integer>();

    @BeforeClass
    public void init() {
        FeeSettingDO feeSettingDO = new FeeSettingDO();
        feeSettingDO.setFsType(1);
        feeSettingDO.setFsChargeType(2);
        feeSettingDO.setFsLoanCategory(3);
        feeSettingDao.insert(feeSettingDO);
        ids.add(feeSettingDO.getFsId());
    }

    @Test
    public void testFindByFeeType() {
        //        List<FeeSettingDO> feeSettingDOs = feeSettingDao.findByFeeType(0, null);
        //        Assert.assertNotNull(feeSettingDOs);
        //        Assert.assertTrue(feeSettingDOs.size() > 0);
        //
        //        for (FeeSettingDO feeSettingDO : feeSettingDOs) {
        //            System.out.println(feeSettingDO);
        //        }
    }

    @Test
    public void testDelete() {
        FeeSettingDO feeSettingDO = feeSettingDao.findById(ids.get(0));
        feeSettingDao.delete(feeSettingDO.getFsId());

        feeSettingDO = feeSettingDao.findById(feeSettingDO.getFsId());
        Assert.assertTrue(feeSettingDO.getFsDeleted() == 1);
    }
}
