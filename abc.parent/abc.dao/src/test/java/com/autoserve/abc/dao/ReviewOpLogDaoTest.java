package com.autoserve.abc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.dao.dataobject.ReviewOpLogDO;
import com.autoserve.abc.dao.intf.ReviewOpLogDao;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-24,17:17
 */
public class ReviewOpLogDaoTest extends BaseDaoTest {
    @Autowired
    private ReviewOpLogDao reviewOpLogDao;
    
    @Test
    public void test1(){
    	List<Integer> reviewIdList = Lists.newArrayList(1409, 1410, 1411);
    	List list = reviewOpLogDao.findMultiReviewOpLogs(reviewIdList);
    	System.out.println(list.size());
    }

    @Test
    public void testFindReviewLastOpLog() {
        ReviewDO reviewDO = new ReviewDO();
        reviewDO.setReviewId(1);
        reviewDO.setReviewLastOpLogId(1);

        ReviewOpLogDO reviewOpLogDO = reviewOpLogDao.findReviewLastOpLog(reviewDO.getReviewId());
        Assert.assertNotNull(reviewOpLogDO);
        Assert.assertTrue(1 == reviewOpLogDO.getRolId());
        Assert.assertTrue(2 == reviewOpLogDO.getRolRoleIdx());
        Assert.assertTrue(22 == reviewOpLogDO.getRolEmpId());

    }
}
