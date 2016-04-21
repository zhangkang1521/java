/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.dao.dataobject.summary.LoanPaymentSummaryDO;
import com.autoserve.abc.dao.dataobject.summary.PaymentPlanSummaryDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.convert.PaymentPlanConverter;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 还款计划服务实现
 * 
 * @author segen189 2014年11月29日 下午2:16:23
 */
@Service
public class PaymentPlanServiceImpl implements PaymentPlanService {

    @Resource
    private PaymentPlanDao paymentPlanDao;

    @Override
    public PlainResult<Integer> batchCreatePaymentPlanList(List<PaymentPlan> paymentPlanList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(paymentPlanList)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "paymentPlanList");
        }

        int count = paymentPlanDao.batchInsert(PaymentPlanConverter.toPaymentPlanDOList(paymentPlanList));
        result.setData(count);
        return result;
    }

    @Override
    public PlainResult<Integer> batchModifyStateByFullTransRecordId(int fullTransRecordId, PayState oldState,
                                                                    PayState newState) {
        if (oldState == null || newState == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILLEGAL_PARAM, "LoanPayState");
        }

        int count = paymentPlanDao.batchUpdateStateByFullTransRecordId(fullTransRecordId, oldState.getState(),
                newState.getState());
        return new PlainResult<Integer>(count);
    }

    @Override
    public PlainResult<Integer> modifyPaymentPlan(String innerSeqNo, PayState oldState, PayState newState,
                                                  Boolean newReplaceState, PaymentPlan paymentPlan) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        if (StringUtil.isBlank(innerSeqNo) || oldState == null || newState == null) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }
        result.setData(paymentPlanDao.updateStateByInnerSeqNo(innerSeqNo, oldState.getState(), newState.getState(),
                newReplaceState));
        return result;
    }

    @Override
    public BaseResult modifyPaymentPlan(int paymentPlanId, BigDecimal punishMoneyIncreasing, PayState newState) {
        if (paymentPlanId <= 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "参数无效");
        }

        int rows = paymentPlanDao.updateByAddPunishMoney(paymentPlanId, punishMoneyIncreasing, newState.getState());

        if (rows > 0) {
            return BaseResult.SUCCESS;
        } else {
            return new BaseResult(CommonResultCode.BIZ_ERROR, "没有还款记录被修改");
        }
    }

    @Override
    public BaseResult modifyPaymentPlan(int paymentPlanId, PayState oldState, PayState newState, PayType newPayType,
                                        String newInnerSeqNo) {
        int count = paymentPlanDao.updateStateTypeInneSeq(paymentPlanId, oldState.getState(), newState.getState(),
                newPayType.getType(), newInnerSeqNo);

        if (count <= 0) {
            BaseResult result = new BaseResult();
            return result.setErrorMessage(CommonResultCode.BIZ_ERROR, "还款计划表状态更改失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<BigDecimal> sumCapitalByLoaneeIdAndLoanId(int loaneeId, int loanId, PayState payPlanState) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal sumCapital = paymentPlanDao.sumCapitalByLoaneeId(loaneeId, loanId, payPlanState == null ? null
                : payPlanState.getState());
        result.setData(sumCapital);
        return result;
    }

    @Override
    public PlainResult<PaymentPlan> queryPaymentPlanByInnerSeqNo(String innerSeqNo) {
        PlainResult<PaymentPlan> result = new PlainResult<PaymentPlan>();

        if (StringUtils.isBlank(innerSeqNo)) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        PaymentPlanDO paymentPlanDO = paymentPlanDao.findPaymentPlanByInnerSeqNo(innerSeqNo);
        result.setData(PaymentPlanConverter.toPaymentPlan(paymentPlanDO));
        return result;
    }

    @Override
    public PageResult<PaymentPlan> queryPaymentPlanList(PaymentPlan pojo, PageCondition pageCondition) {
        PageResult<PaymentPlan> result = new PageResult<PaymentPlan>(pageCondition.getPage(),
                pageCondition.getPageSize());

        int totalCount = paymentPlanDao.countListByParam(PaymentPlanConverter.toPaymentPlanDO(pojo));
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(PaymentPlanConverter.toPaymentPlanList(paymentPlanDao.findListByParam(
                    PaymentPlanConverter.toPaymentPlanDO(pojo), pageCondition)));
        }

        return result;
    }

    @Override
    public PageResult<PaymentPlanJDO> queryPlanList(PageCondition pageCondition) {
        PageResult<PaymentPlanJDO> result = new PageResult<PaymentPlanJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());

        int totalCount = paymentPlanDao.countPlanList();
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<PaymentPlanJDO> list = paymentPlanDao.findPlanList(pageCondition);
            result.setData(list);
        }

        return result;
    }
    
    @Override
    public PageResult<PaymentPlanJDO> queryPlanList2(PaymentPlanSearchDO pp, PageCondition pageCondition) {
        PageResult<PaymentPlanJDO> result = new PageResult<PaymentPlanJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());

        int totalCount = paymentPlanDao.countPlanList2(pp);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<PaymentPlanJDO> list = paymentPlanDao.findPlanList2(pp, pageCondition);
            result.setData(list);
        }

        return result;
    }

    @Override
    public PlainResult<PaymentPlan> queryNextPaymentPlan(int loanId) {
        PlainResult<PaymentPlan> result = new PlainResult<PaymentPlan>();
        result.setData(PaymentPlanConverter.toPaymentPlan(paymentPlanDao.findNextPaymentPlan(loanId,
                PayState.UNCLEAR.getState())));
        return result;
    }

    @Override
    public ListResult<PaymentPlan> queryPaymentPlanList(int fullTransferRecordId) {
        ListResult<PaymentPlan> result = new ListResult<PaymentPlan>();

        if (fullTransferRecordId <= 0) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM);
        }

        List<PaymentPlan> data = PaymentPlanConverter.toPaymentPlanList(paymentPlanDao
                .findByFullTransferRecordId(fullTransferRecordId));

        result.setData(data);
        return result;
    }

    @Override
    public ListResult<LoanPaymentSummaryDO> querySummaryByIds(List<Integer> loanIds) {
        ListResult<LoanPaymentSummaryDO> result = new ListResult<LoanPaymentSummaryDO>();

        if (CollectionUtils.isEmpty(loanIds)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "loanIds");
        }

        List<LoanPaymentSummaryDO> data = paymentPlanDao.findSummaryListByIds(loanIds,
                Arrays.asList(PayState.CLEAR.getState()));

        result.setData(data);
        return result;
    }

    @Override
    public PlainResult<PaymentPlan> queryNextPaymentPlan(int loanId, InvestSearchJDO investSearchJDO) {
        PlainResult<PaymentPlan> result = new PlainResult<PaymentPlan>();
        result.setData(PaymentPlanConverter.toPaymentPlan(paymentPlanDao.findNextPaymentPlanBySearch(loanId,
                PayState.UNCLEAR.getState(), investSearchJDO)));
        return result;
    }

    @Override
    public PlainResult<PaymentPlanSummaryDO> queryTotalByLoanId(int loanId, InvestSearchJDO investSearchJDO) {
        PlainResult<PaymentPlanSummaryDO> result = new PlainResult<PaymentPlanSummaryDO>();
        result.setData(this.paymentPlanDao.findTotalByLoanId(loanId, investSearchJDO));
        return result;
    }

    @Override
    public PlainResult<PaymentPlan> queryById(int planId) {

        PaymentPlanDO paymentPlanDO = this.paymentPlanDao.findById(planId);

        PlainResult<PaymentPlan> result = new PlainResult<PaymentPlan>();

        result.setData(PaymentPlanConverter.toPaymentPlan(paymentPlanDO));

        return result;
    }

    @Override
    public ListResult<PaymentPlanDO> queryByLoanId(int loanId) {
        ListResult<PaymentPlanDO> result = new ListResult<PaymentPlanDO>();
        List<Integer> loanIds = new ArrayList<Integer>();
        loanIds.add(loanId);
        List<PaymentPlanDO> data = paymentPlanDao.findByLoanId(loanId);
        result.setData(data);
        return result;
    }

    @Override
    public PlainResult<BigDecimal> queryCurMonthWaitPayCapital(int userId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal capital = paymentPlanDao.queryCurMonthWaitPayCapital(userId);
        result.setData(capital);
        return result;
    }

    @Override
    public PlainResult<BigDecimal> queryCurMonthWaitPayInterest(int userId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        BigDecimal capital = paymentPlanDao.queryCurMonthWaitPayInterest(userId);
        result.setData(capital);
        return result;
    }

	@Override
	public PlainResult<BigDecimal> queryRemainPrincipalByLoanidAndPeriod(
			int loanId, int period) {
		PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
		BigDecimal capitalSum =paymentPlanDao.queryRemainPrincipalByLoanidAndPeriod(loanId,period);
		result.setData(capitalSum);
		return result;
	}

	@Override
	public PlainResult<BigDecimal> queryWaitPayCapital(int userId) {
		 PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
	     BigDecimal capital = paymentPlanDao.queryWaitPayCapital(userId);
	     result.setData(capital);
	     return result;
	}

	@Override
	public PageResult<PaymentPlan> queryPaymentPlanByDay(Integer userId,
			PageCondition pageCondition) {
		PageResult<PaymentPlan> result = new PageResult<PaymentPlan>(pageCondition.getPage(),
	             pageCondition.getPageSize());

	     int totalCount = paymentPlanDao.countQueryPaymentPlanByDay(userId);
	     result.setTotalCount(totalCount);

	     if (totalCount > 0) {
	         result.setData(PaymentPlanConverter.toPaymentPlanList(paymentPlanDao.queryPaymentPlanByDay(userId, pageCondition)));
	     }
	     return result;
	}	 
}
