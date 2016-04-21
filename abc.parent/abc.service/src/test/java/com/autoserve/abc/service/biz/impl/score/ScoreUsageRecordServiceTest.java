package com.autoserve.abc.service.biz.impl.score;

import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.score.ScoreUsageRecordService;
import com.autoserve.abc.service.biz.result.BaseResult;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/27 18:04.
 */
public class ScoreUsageRecordServiceTest extends BaseServiceTest {
    @Resource
    private ScoreUsageRecordService scoreUsageRecordService;

    @Test
    public void testModifyScoreUsageRecordAfterReview(){
        BaseResult result = scoreUsageRecordService.modifyScoreUsageRecordAfterReview(1, ReviewState.PASS_REVIEW);
        System.out.println(result.getMessage());
    }
}
