package com.autoserve.abc.web.module.screen.loanpay;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.NumberUtil;

/**
 * 类RepaymentView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年1月10日 下午9:52:23
 */
public class RepaymentView {

    @Resource
    private PaymentPlanService paymentPlanService;
    @Resource
    private RepayService repayService;

    public void execute(Context context, ParameterParser params) {
        int loanId = params.getInt("loanId");

        int planId = params.getInt("planId");
        PlainResult<BigDecimal> overResult = repayService.queryPulishMoney(planId);
        PlainResult<PaymentPlan> result = this.paymentPlanService.queryById(planId);
        context.put("loanId", loanId);
        context.put("plan", result.getData());
        context.put("planId", planId);
        context.put("overMonery", NumberUtil.moneyFormat(overResult.getData().doubleValue()));
    }
}
