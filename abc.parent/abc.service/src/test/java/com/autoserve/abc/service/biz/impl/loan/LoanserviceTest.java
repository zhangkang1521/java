package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.LoanService;

public class LoanserviceTest extends BaseServiceTest {
	@Resource
	private LoanService loanService;

	@Test
	public void test() {
		DateTime dateTime = new DateTime(new Date());

		String loanNo = "投资啊";
		BigDecimal loanMoney = new BigDecimal("2000");
		Loan loan = new Loan();

		loan.setLoanNo(loanNo);
		loan.setLoanUserId(132);
		loan.setLoanMoney(loanMoney);
		loan.setLoanRate(new BigDecimal("12"));
		loan.setLoanPeriod(3);
		loan.setLoanPeriodUnit(LoanPeriodUnit.MONTH);
		loan.setLoanMinInvest(new BigDecimal("100"));
		loan.setLoanMaxInvest(loanMoney);
		loan.setLoanPayType(LoanPayType.AYHX_DQHB);
		loan.setLoanInvestEndtime(dateTime.plusMonths(1).toDate());//投资结束时间
		loan.setLoanExpireDate(dateTime.plusMonths(3).toDate());//项目到期日
		loan.setLoanInvestStarttime(dateTime.toDate());//投资开始时间
		loan.setLoanPayDate(15);// 付息日
		loan.setLoanClearType(LoanClearType.FIXED_DAY);
		loan.setInvestReduseRatio(0.0);
		loan.setInvestRedsendRatio(0.0);
		loan.setLoanUse("借款用途:" + loanNo);
		loan.setLoanSecondaryAllocation("2");// 不分配
		loan.setLoanFromIntent(false);// 是否来自前台意向
		loan.setLoanCreator(135);
		loan.setLoanCreatetime(dateTime.toDate());
		loan.setLoanState(LoanState.BID_INVITING);
		loan.setLoanCategory(LoanCategory.LOAN_PERSON);
		loan.setLoanFileUrl("ebbad310-caee-478b-8808-2c9251f7d4e1");
		loanService.createLoan(loan);
	}
}
