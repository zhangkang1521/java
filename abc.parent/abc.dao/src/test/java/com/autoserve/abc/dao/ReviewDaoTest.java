package com.autoserve.abc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.dao.dataobject.search.LoanReviewSearchDO;
import com.autoserve.abc.dao.intf.ReviewDao;
import com.google.common.collect.Lists;

/**
 * @author yuqing.zheng Created on 2014-11-24,21:03
 */
public class ReviewDaoTest extends BaseDaoTest {
    @Autowired
    private ReviewDao reviewDao;

    @Test
    public void testFind() {
        PageCondition pageCondition = new PageCondition(1, 10);
        ReviewDO reviewDO = new ReviewDO();
        reviewDO.setReviewType(3);
        reviewDO.setReviewCurrRoleIdx(2);
        reviewDO.setReviewSuspend(false);

        List<ReviewDO> reviews = reviewDao.find(reviewDO, pageCondition);
        Assert.assertNotNull(reviews);
        System.out.println(reviews.size());
        Assert.assertTrue(reviews.size() > 0);
    }

    @Test
    public void testCountByTypeRoles() {
        int count = reviewDao.countByTypeRoles(4, Lists.newArrayList(2, 3));
        System.out.println(count);
    }

    @Test
    public void testFindByTypeRoles() {
        List<ReviewDO> reviewDOs = reviewDao.findByTypeRoles(4, Lists.newArrayList(2, 3), new PageCondition(1, 10));
        Assert.assertNotNull(reviewDOs);
        Assert.assertTrue(reviewDOs.size() > 0);
        System.out.println(reviewDOs.size());
    }

    @Test
    public void testFindByTypeApplyIdList() {
        List<ReviewDO> reviewDOs = reviewDao.findByTypeApplyIdList(4, Lists.newArrayList(1, 2, 3, 4, 5));
        Assert.assertNotNull(reviewDOs);
        Assert.assertTrue(reviewDOs.size() > 0);
        System.out.println(reviewDOs.size());
    }

    @Test
    public void testSearch() {
        LoanReviewSearchDO searchDO = new LoanReviewSearchDO();
        searchDO.setReviewType(2);
        searchDO.setLoanUser("胖");

        List<ReviewDO> reviewDOs = reviewDao.searchLoanReview(searchDO, null);

        Assert.assertNotNull(reviewDOs);
        Assert.assertTrue(reviewDOs.size() > 0);
        System.out.println(reviewDOs.size());

        int count = reviewDao.countForLoanReviewSearch(searchDO);
        System.out.println("count: " + count);
    }

}
