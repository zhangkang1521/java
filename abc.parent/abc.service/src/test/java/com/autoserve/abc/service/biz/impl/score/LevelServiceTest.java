package com.autoserve.abc.service.biz.impl.score;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.intf.LevelDao;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.score.LevelService;

/**
 * @author RJQ 2014/11/22 11:01.
 */
public class LevelServiceTest extends BaseServiceTest {
    @Autowired
    private LevelService levelService;

    @Autowired
    private LevelDao     levelDao;

    @Test
    public void testFindByScore() {
        LevelDO levelDO = levelService.findLevelByScore(new BigDecimal(17)).getData();
        System.out.println("!!!!!!!!!!!!!!" + levelDO.getLevName());
    }

    @Test
    public void testQueryList() {
//        int size = levelService.findAllLevel().getData().size();
//        System.out.println(size);
    }
}
