package com.autoserve.abc.dao;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.dao.intf.ScoreHistoryDao;
import com.autoserve.abc.dao.intf.ScoreUsageRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author RJQ 2014/12/16 11:37.
 */
public class ScoreDaoTest extends BaseDaoTest {
    @Autowired
    private ScoreHistoryDao scoreHistoryDao;

    @Autowired
    private ScoreUsageRecordDao scoreUsageRecordDao;

    @Test
    public void testFindListByParam(){
        List<ScoreHistoryWithValueDO> list = scoreHistoryDao.findListByParam(new ScoreHistoryDO(), null);
        System.out.println(list.size());
    }

    @Test
    public void testFindListByParam1(){
        List<ScoreUsageRecordWithNameDO> result = scoreUsageRecordDao.findListByParam(new ScoreUsageRecordDO(), new PageCondition());
        System.out.println(result.size());
    }

    @Test
    public void testFindWithNameById(){
        ScoreUsageRecordWithNameDO nameDO = scoreUsageRecordDao.findWithNameById(1);
        System.out.println(nameDO.getUserName());
    }
}
