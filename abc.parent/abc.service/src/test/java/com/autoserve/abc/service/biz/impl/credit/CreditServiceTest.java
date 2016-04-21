package com.autoserve.abc.service.biz.impl.credit;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.search.CreditSearchDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2015/1/4 18:39.
 */
public class CreditServiceTest extends BaseServiceTest {
    @Autowired
    private CreditService creditService;

    @Test
    public void testQueryList() {
        CreditJDO creditJDO = new CreditJDO();
        PageResult<CreditJDO> result = creditService.queryList(creditJDO, new PageCondition());
        System.out.println(result.getDataSize());
    }

    @Test
    public void testDindFullCreditInfoById() {
        PlainResult<CreditJDO> result = creditService.findFullCreditInfoById(1);
        System.out.println(result.getMessage());
    }

    @Test
    public void testModifyCreditAfterReview() {
        // BigDecimal bigDecimal = new BigDecimal(10000);
        //        creditService.modifyCreditAfterReview(1,1,bigDecimal, true);
    }

    @Test
    public void testSearchList() {
        CreditSearchDO creditSearchDO = new CreditSearchDO();
        //        creditSearchDO.setUserName("胖胖");
        //        creditSearchDO.setCreditReviewStartAmount(new BigDecimal(1000));
        //        creditSearchDO.setCreditReviewEndAmount(new BigDecimal(1000));
        //        creditSearchDO.setCreditReviewState(0);
        creditSearchDO.setCreditType(1);
        PageResult<CreditJDO> re = creditService.searchList(creditSearchDO, new PageCondition());
        System.out.println(re.getDataSize());
    }
}
