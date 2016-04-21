/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;

/**
 * LoanIntentAppplyService的单元测试
 * 
 * @author Lady 2014年12月22日 下午7:56:54
 */
public class LoanIntentAppplyServiceTest extends BaseServiceTest {

    @Autowired
    private LoanIntentApplyService loanIntentAppplyService;

    @Test
    public void testLoanIntentAppplyService() {
        LoanIntentApply pojo = new LoanIntentApply();
        pojo.setUserName("小建清");
        pojo.setUserId(1);
        pojo.setIntentCategory(LoanCategory.LOAN_PERSON);
        //        pojo.setIntentPayType(LoanPayType.DEBX);
        pojo.setIntenteeType(LoaneeType.PERSON);
        pojo.setIntentMoney(BigDecimal.valueOf(10000));
        pojo.setIntentPeriod(12);
        pojo.setIntentPeriodUnit(LoanPeriodUnit.MONTH);
        pojo.setIntentRate(BigDecimal.valueOf(0.12));
        pojo.setIntentState(LoanState.WAIT_INTENT_REVIEW);
        pojo.setIntentTime(new Date());
        pojo.setIntentTitle("借点钱花花");
        pojo.setIntentUse("你管我");
        loanIntentAppplyService.createLoanIntentApply(pojo);
    }

}
