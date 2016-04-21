/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;

/**
 * 类DealRecordTest.java的实现描述：TODO 类实现描述
 * 
 * @author J.YL 2014年11月22日 下午8:44:26
 */
@Test
public class DealRecordTest extends BaseDaoTest {

    @Resource
    private DealRecordDao dao;

    @Test
    public void batchInsertTest() {
        List<DealRecordDO> dealList = new ArrayList<DealRecordDO>();
        DealRecordDO d1 = new DealRecordDO();
        DealRecordDO d2 = new DealRecordDO();
        DealRecordDO d3 = new DealRecordDO();

        d1.setDrBusinessId(0);
        d1.setDrInnerSeqNo("testSeqNo-dealRecord1");
        d1.setDrOperateDate(new Date());
        d1.setDrState(0);
        d1.setDrCashId(1);

        d2.setDrBusinessId(1);
        d2.setDrInnerSeqNo("testSeqNo-dealRecord2");
        d2.setDrOperateDate(new Date());
        d2.setDrState(0);

        d3.setDrBusinessId(2);
        d3.setDrInnerSeqNo("testSeqNo-dealRecord3");
        d3.setDrOperateDate(new Date());
        d3.setDrState(0);
        dealList.add(d1);
        //     dealList.add(d2);
        //    dealList.add(d3);
        int flag = dao.batchInsert(dealList);
        System.out.println("aaaa" + flag);
    }
}
