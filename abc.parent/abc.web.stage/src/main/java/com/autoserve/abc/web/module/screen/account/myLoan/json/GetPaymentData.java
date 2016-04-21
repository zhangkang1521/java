package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.module.screen.account.myLoan.PaymentPlanVO;

public class GetPaymentData
{
	@Resource
    private PaymentPlanService paymentPlanService;
	@Resource
	private LoanQueryService loanService;
    @Resource
    private SysConfigService      sysConfigService;
	 public  List<PaymentPlanVO> execute(Context context, ParameterParser params)
	 {
		
		 Integer loanId = params.getInt("loanId");
		 PaymentPlan paymentPlan = new PaymentPlan();
 		 paymentPlan.setLoanId(loanId); 		
		 PageResult<PaymentPlan> paymentresult = paymentPlanService.queryPaymentPlanList(paymentPlan, new PageCondition(1,65535,"loanPeriod",Order.ASC));
		 List<PaymentPlan> paymentList = paymentresult.getData();
		 List<PaymentPlanVO> paymentPlanVOList = new ArrayList<PaymentPlanVO>();
		 for (PaymentPlan paymentPlan2 : paymentList)
		{
			 PaymentPlanVO vo = new PaymentPlanVO();
			 vo.setId(paymentPlan2.getId());
			 vo.setPayTime(paymentPlan2.getPaytime());
			 vo.setLoanId(paymentPlan2.getLoanId());
			 vo.setLoanPeriod(paymentPlan2.getLoanPeriod());
			 if(paymentPlan2.getPayState()==PayState.CLEAR){
				 vo.setIsClear(1);
				 vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()));
			 }
			 if(paymentPlan2.getPayState()==PayState.UNCLEAR){
				 vo.setIsClear(0);
				//判断是否逾期，同时计算罚金
		         BigDecimal pulishMoney = computePulishMoney(paymentPlan2);
		         vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()).add(pulishMoney));
			 }			 
			// 应还
			 vo.setPayCapital(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()));
			 vo.setPayInterest(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest()));			
//			 vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()));
			 vo.setPayServiceFee(paymentPlan2.getPayServiceFee().subtract(paymentPlan2.getCollectServiceFee()));
			 //实还
			 vo.setPayCollectCapital(paymentPlan2.getPayCollectCapital());
			 vo.setPayCollectInterest(paymentPlan2.getPayCollectInterest());
			 vo.setPayCollectFine(paymentPlan2.getPayCollectFine());
			 vo.setCollectServiceFee(paymentPlan2.getCollectServiceFee());
			 Integer loanid = paymentPlan2.getLoanId();
			 PlainResult<Loan> loanResult = loanService.queryById(loanid);
			 Loan loan = loanResult.getData();
			 vo.setLoanNo(loan.getLoanNo());
			 paymentPlanVOList.add(vo);
		}
		 
		 return paymentPlanVOList;
	 }
	 
	 
	 /**
	     * 查询罚息利率并计算罚息
	     */
	    private BigDecimal computePulishMoney(PaymentPlan repayPlan) {
	        BigDecimal pulishMoney;

	        // 判断是否逾期
	        DateTime nowTime = DateTime.now();
	        DateTime planPayTime = new DateTime(repayPlan.getPaytime());

	        // 如果逾期则查询罚息利率并计算罚息
	        if (nowTime.isAfter(planPayTime) && !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
	        	//罚息利率
	            PlainResult<SysConfig> punishRateResult = sysConfigService
	                    .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
	            if (!punishRateResult.isSuccess()) {
	                throw new BusinessException("罚息利率查询失败");
	            }
//	            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
	            
	            //天罚息利率
	            BigDecimal rate = new BigDecimal(punishRateResult.getData().getConfValue()).divide(
	                    new BigDecimal(100 * 360), 10,BigDecimal.ROUND_HALF_UP);
	            double punishRate = rate.doubleValue();
	            
	            //天数
	            int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
	            //未还本金
	            PlainResult<BigDecimal> remainingPrincipalResult=paymentPlanService.queryRemainPrincipalByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
	            BigDecimal remainingPrincipal=remainingPrincipalResult.getData();
	            /**
	             * 罚息 = 剩余本金 * 罚息利率 * 逾期天数 + 剩余罚金<br>
	             * 剩余本金 = 应还本金 - 实还本金<br>
	             * 罚息利率=罚息月利率/100/30<br>
	             * 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或 应还日期（如果借款人没有还过款））<br>
	             */

	            /**
	             * 当前为 罚金 = 未还本金* 天罚息利率* 逾期天数<br>
	             */
	            pulishMoney = remainingPrincipal.multiply(new BigDecimal(punishRate * expiryDays)).setScale(2,BigDecimal.ROUND_HALF_UP);
	            
//	            pulishMoney=pulishMoney.abs(mc);
	        } else {
	            pulishMoney = BigDecimal.ZERO;
	        }

	        return pulishMoney;
	    }
}
