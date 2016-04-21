package com.autoserve.abc.service.biz.impl.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author RJQ 2014/11/22 15:49.
 */
public class ScoreServiceTest extends BaseServiceTest {
    @Autowired
    private ScoreService scoreService;

    @Test
    public void testQueryList() {
        List<ScoreDO> list = scoreService.queryScoreList(new ScoreDO(), new PageCondition()).getData();
        System.out.println(list.size());
    }

    @Test
    public void testCreateScore() {
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreName("测试积分");
        scoreDO.setScoreCode("32423adsf");
        scoreDO.setScoreValue(10);
        scoreDO.setScoreState(1);
        scoreService.createScore(scoreDO);
    }

    @Test
    public void testModifyScore() {
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreId(3);
        scoreDO.setScoreName("测试积分1");
        scoreDO.setScoreCode("111111");
        scoreDO.setScoreState(2);
        scoreService.modifyScore(scoreDO);
    }

    @Test
    public void testEnableScore() {
        scoreService.enableScore(3);
    }

    @Test
    public void testChangeUserScore() {
//        scoreService.changeUserScore(1, ScoreType.BIND_CARD.getCode(), null);
//        scoreService.changeUserScore(1, 1, null);
    }

//    @Test
//    public void testQueryScoreHistoryList(){
//        List<ScoreHistoryWithValueDO> list = scoreService.queryScoreHistoryList(new ScoreHistoryDO(), new PageCondition()).getData();
//        System.out.println("~~~~~~~~~~~~~~~"+list.size());
//    }

    @Test
    public void testFindUserScoreDetail(){
//        UserScoreDetail userScoreDetail = scoreService.findUserScoreDetail(1).getData();
//        System.out.println("~~~~~~~~~~~~~~~~"+userScoreDetail.toString());
    }

//    @Test
//    public void testQueryScoreUsageRecordList(){
//        PageResult<ScoreUsageRecordWithNameDO> result = scoreService.queryScoreUsageRecordList(new ScoreUsageRecordDO(), new PageCondition());
//        System.out.println(result.getMessage());
//    }
}
