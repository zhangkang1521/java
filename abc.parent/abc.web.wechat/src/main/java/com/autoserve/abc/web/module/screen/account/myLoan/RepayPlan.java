package com.autoserve.abc.web.module.screen.account.myLoan;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.formula.SubtractPtg;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;


public class RepayPlan {
	@Autowired
    private HttpSession session;
	@Resource
    private PaymentPlanService paymentPlanService;
    public void execute(Context context, ParameterParser params) {
    	 User user = (User) session.getAttribute("user");
    	 int currentPage = params.getInt("currentPage");
    	 if(currentPage==0)currentPage=1;
    	 int pageSize =5;//默认情况
    	 PaymentPlan paymentPlan = new PaymentPlan();
    	 paymentPlan.setLoanee(user.getUserId());
    	 PageCondition pageCondition = new PageCondition(currentPage,pageSize,"paytime",Order.DESC);
    	 PageResult<PaymentPlan> result = paymentPlanService.queryPaymentPlanList(paymentPlan, pageCondition);
    	 List<PaymentPlan> paymentPlanList = result.getData();
    	 Pagebean<PaymentPlan> pagebean = new Pagebean<PaymentPlan>(currentPage,pageSize,paymentPlanList,result.getTotalCount());
    	 PageResult<PaymentPlan> resultAll = paymentPlanService.queryPaymentPlanList(paymentPlan, new PageCondition(1,result.getTotalCount()));
    	 BigDecimal toPayCapital = new BigDecimal(0.00);
    	 BigDecimal toPayInterest = new BigDecimal(0.00);
    	 for (PaymentPlan paymentPlan2 : resultAll.getData())//计算总额
		{
//			if(paymentPlan2.getPayState()!=null &&(paymentPlan2.getPayState()==PayState.UNCLEAR || paymentPlan2.getPayState()==PayState.PAYING))
//			{
				//计算总待付本金
				toPayCapital=toPayCapital.add(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()));
				//计算总待付利息
				toPayInterest=toPayInterest.add(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest()));
//			}
		}
    	 context.put("toPayCapital", toPayCapital);
    	 context.put("toPayInterest", toPayInterest);
    	 context.put("pagebean", pagebean);
    }
}
