package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.ReviewSendLogDO;
import com.autoserve.abc.dao.intf.ReviewSendLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-30,19:03
 */
public class ReviewSendLogDaoTest extends BaseDaoTest {
    @Autowired
    private ReviewSendLogDao reviewSendLogDao;

    @Test
    public void testInsert() {
        ReviewSendLogDO sendDO = new ReviewSendLogDO();

        sendDO.setRslReviewId(1);
        sendDO.setRslReviewVersion(4);
        sendDO.setRslPrevId(99);
        sendDO.setRslFromRole(2);
        sendDO.setRslFromEmp(2);
        sendDO.setRslToRole(5);
        sendDO.setRslToEmp(3);
        sendDO.setRslNextReviewId(5);

        reviewSendLogDao.insert(sendDO);
    }
}
