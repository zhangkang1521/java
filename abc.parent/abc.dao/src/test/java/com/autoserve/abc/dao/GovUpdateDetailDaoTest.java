package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import com.autoserve.abc.dao.intf.GovernmentUpdateDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/11/19 14:36.
 */
public class GovUpdateDetailDaoTest extends BaseDaoTest {
    @Autowired
    private GovernmentUpdateDetailDao dao;

    @Test
    public void testBatchInsert() {
        List<GovernmentUpdateDetailDO> list = new ArrayList<GovernmentUpdateDetailDO>();
        GovernmentUpdateDetailDO detailDO = new GovernmentUpdateDetailDO();
        detailDO.setGuhUpdateHistoryId(2);
        detailDO.setGuhField("gov_name");
        detailDO.setGuhFieldOld("修改前值1");
        detailDO.setGuhFiledNew("修改后值1");
        list.add(detailDO);

        GovernmentUpdateDetailDO detailDO2 = new GovernmentUpdateDetailDO();
        detailDO2.setGuhUpdateHistoryId(2);
        detailDO2.setGuhField("gov_email");
        detailDO2.setGuhFieldOld("修改前值2");
        detailDO2.setGuhFiledNew("修改后值2");
        list.add(detailDO2);

        int value = dao.batchInsert(list);
        System.out.println(value);
    }
}
