package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.intf.LevelDao;

/**
 * @author RJQ 2014/12/17 16:41.
 */
public class LoginLogDaoTest extends BaseDaoTest {
    @Autowired
    private LevelDao levelDao;

    @Test
    public void testBatchInsert() {
        List<LevelDO> levelDOs = new ArrayList<LevelDO>();
        LevelDO levelDO = new LevelDO();
        levelDO.setLevName("测试名字1");
        levelDO.setLevMinScore(new BigDecimal(1));
        levelDO.setLevMaxScore(new BigDecimal(9));
        levelDO.setLevIcon("asd");
        levelDOs.add(levelDO);
        LevelDO levelDO2 = new LevelDO();
        levelDO2.setLevName("测试名字2");
        levelDO2.setLevMinScore(new BigDecimal(10));
        levelDO2.setLevMaxScore(new BigDecimal(19));
        levelDO2.setLevIcon("asd");
        levelDOs.add(levelDO2);

        int val = levelDao.batchInsert(levelDOs);
        System.out.println(val);
    }

    @Test
    public void testFindByScore() {
        LevelDO score = levelDao.findByScore(new BigDecimal(55));
        System.out.println(score.getLevName());
    }
}
