/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.convert.PaymentPlanConverter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 还款交易执行后的回调函数
 * 
 * @author segen189 2014年12月19日 下午9:06:29
 */
@Service
public class RepayedCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(RepayedCallback.class);

    @Resource
    private LoanDao             loanDao;

    @Resource
    private PaymentPlanService  paymentPlanService;

    @Resource
    private IncomePlanService   incomePlanService;

    @Resource
    private UserService         userService;

    @Resource
    private LoanService         loanService;

    @Resource
    private PaymentPlanDao      paymentPlanDao;

    @Resource
    private IncomePlanDao       incomePlanDao;
    
    @Resource
    private SysConfigService 	sysConfigService;
    
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;
    
    @Resource
    private TransferLoanDao transferLoanDao;

    @Resource
    private SmsNotifyDao smsNotifyDao;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doRepayedSuccess(data);
            case FAILURE:
                return doRepayedFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    // TODO  还款的时候去改机构信息（可用担保额度）
    // 添加还款记录
    private BaseResult doRepayedSuccess(DealNotify data) {
        BaseResult result = new BaseResult();

        PlainResult<PaymentPlan> paymentResult = paymentPlanService.queryPaymentPlanByInnerSeqNo(data.getInnerSeqNo());
        if (!paymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款计划查询失败");
        }

        final PaymentPlan paymentPlan = paymentResult.getData();
        if (paymentPlan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "根据内部交易流水号未查到还款计划");
        }

        PlainResult<PaymentPlan> nextPaymentResult = paymentPlanService.queryNextPaymentPlan(paymentPlan.getLoanId());
        if (!nextPaymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "下一期还款计划查询失败");
        }

        // 更新还款计划状态
        PayState newPayState;
        if (PayType.COMMON_CLEAR == paymentPlan.getPayType() || PayType.FORCE_CLEAR == paymentPlan.getPayType()) {
            newPayState = PayState.CLEAR;
        } else {
            newPayState = PayState.UNCLEAR;
        }

        //更新已还信息
        if (paymentPlan != null) {
            paymentPlan.setPayCollectCapital(paymentPlan.getPayCapital());
            paymentPlan.setPayCollectFine(paymentPlan.getPayFine());
            paymentPlan.setPayCollectInterest(paymentPlan.getPayInterest());
            paymentPlan.setCollectServiceFee(paymentPlan.getPayServiceFee());
            paymentPlan.setCollectGuarFee(paymentPlan.getCollectGuarFee());
            paymentPlan.setCollectTotal(paymentPlan.getPayTotalMoney());
            this.paymentPlanDao.update(PaymentPlanConverter.toPaymentPlanDO(paymentPlan));
        }

        //更新收益已还信息
        this.incomePlanDao.batchIncomeMoneryUpdate(paymentPlan.getId());
        result = paymentPlanService.modifyPaymentPlan(data.getInnerSeqNo(), PayState.PAYING, newPayState,
                PayType.PLA_CLEAR.equals(paymentPlan.getPayType()), paymentPlan);

        if (!result.isSuccess()) {
            log.warn("批量修改还款计划状态失败！{}", result.getMessage());
            throw new BusinessException("批量修改还款计划状态失败！");
        }

        // 全部还款都还清时
        if (PayState.CLEAR.equals(newPayState) && nextPaymentResult.getData() == null) {
            // 更新loan状态
            // 项目跟踪状态记录
            LoanTraceRecord traceRecord = new LoanTraceRecord();
            traceRecord.setCreator(0);
            traceRecord.setLoanId(paymentPlan.getLoanId());
            traceRecord.setLoanTraceOperation(LoanTraceOperation.repayedCompleted);
            traceRecord.setOldLoanState(LoanState.REPAYING);
            traceRecord.setNewLoanState(LoanState.REPAY_COMPLETED);
            traceRecord.setNote("借款全部还款已结束，loanId=" + paymentPlan.getLoanId());

            BaseResult modResult = loanService.changeLoanState(traceRecord);
            if (!modResult.isSuccess()) {
                log.warn(modResult.getMessage());
                throw new BusinessException("借款全部还款已结束，普通标状态修改失败");
            }

            // 更新借款人的信用额度
//            LoanDO loanDO = loanDao.findById(paymentPlan.getLoanId());// COULD NOT BE NULL
//            BaseResult addCreditResult = userService.addSettCredit(paymentPlan.getLoanee(), loanDO.getLoanMoney());
//            if (!addCreditResult.isSuccess()) {
//                log.warn(addCreditResult.getMessage());
//                throw new BusinessException("借款全部还款已结束，借款人的信用额度更新失败");
//            }
            if (!paymentPlan.getReplaceState()) {
                // 更新收益计划状态，投资状态
                PlainResult<Integer> oldModResult = incomePlanService.batchModifyIncomePlanAndInvest(null,
                        paymentPlan.getLoanId(), null, IncomePlanState.PAYING, IncomePlanState.CLEARED,
                        InvestState.EARNING, InvestState.EARN_COMPLETED);
                if (!oldModResult.isSuccess() || oldModResult.getData() <= 0) {
                    log.warn("借款全部还款已结束，投资人投资记录状态更新失败！{}", oldModResult.getMessage());
                    throw new BusinessException("借款全部还款已结束，投资人投资记录状态更新失败");
                }
            }

            // 借款已结清，增加积分
            //            BaseResult scoreAddResult = userService.modifyUserScore(loanDO.getLoanUserId(), ScoreType.REFUND_SETTLE,
            //                    "借款已结清，系统赠送用户积分");
            //            if (!scoreAddResult.isSuccess()) {
            //                log.warn(scoreAddResult.getMessage());
            //                //throw new BusinessException("赠送用户积分失败"); TODO THROW OR NOT
            //            }

        } else {
            if (!paymentPlan.getReplaceState()) {
                // 更新收益计划状态
                result = incomePlanService.batchModifyStateByInnerSeqNo(data.getInnerSeqNo(), IncomePlanState.PAYING,
                        IncomePlanState.CLEARED);
                if (!result.isSuccess()) {
                    log.warn("批量修改收益计划状态失败！{}", result.getMessage());
                    throw new BusinessException("批量修改收益计划状态失败！");
                }
            }
        }

        // 借款已还款，增加积分
        //        ScoreType scoreType;
        //        switch (paymentPlan.getPayType()) {
        //            case COMMON_CLEAR: {
        //                scoreType = ScoreType.NORMAL_REFUND;
        //                break;
        //            }
        //            case FORCE_CLEAR: {
        //                scoreType = ScoreType.FORCE_REFUND;
        //                break;
        //            }
        //            case PLA_CLEAR: {
        //                scoreType = ScoreType.SYSTEM_REFUND;
        //                break;
        //            }
        //            default:
        //                scoreType = null;
        //        }
        //
        //        BaseResult scoreAddResult = userService.modifyUserScore(paymentPlan.getLoanee(), scoreType, "借款已还款，系统赠送用户积分");
        //        if (!scoreAddResult.isSuccess()) {
        //            log.warn(scoreAddResult.getMessage());
        //            //throw new BusinessException("赠送用户积分失败"); TODO THROW OR NOT
        //        }

        // 短信通知
        try {
        	SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_REPAYMENT_CFG).getData().getConfValue(), SmsNotifyCfg.class);
            if(smsNotifyCfg.getSwitchState() == 1) {
            	String pattern = smsNotifyCfg.getContentTemplate();
            	String loanNo = null;
            	FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findById(paymentPlan.getFullTransRecordId());
            	if(fullTransferRecordDO.getFtrBidType() == 0) { // 正常标
            		LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
            		loanNo = loanDo.getLoanNo();
            		
            	} else if(fullTransferRecordDO.getFtrBidType() == 1) { // 转让标
            		TransferLoanDO loanDo = transferLoanDao.findById(fullTransferRecordDO.getFtrBidId());
            		loanNo = loanDo.getTlLoanNo();
            		
            	}
            	List<Map<String, Object>> list = incomePlanDao.findUserMapByPaymentPlanId(paymentPlan.getId());
            	for (Map<String, Object> map : list) {
            		SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
            		String content = MessageFormat.format(pattern, loanNo, map.get("money"));
            		smsNotifyDO.setReceivePhone((String)map.get("phone"));
                	smsNotifyDO.setContent(content);
                	smsNotifyDO.setCreateTime(new Date());
                	smsNotifyDO.setSendStatus(0);
                	smsNotifyDO.setSendCount(0);
                	smsNotifyDao.insert(smsNotifyDO);
				}
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }

    private BaseResult doRepayedFailure(DealNotify data) {
        PlainResult<PaymentPlan> paymentResult = paymentPlanService.queryPaymentPlanByInnerSeqNo(data.getInnerSeqNo());
        if (!paymentResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "还款计划查询失败");
        }

        final PaymentPlan paymentPlan = paymentResult.getData();
        if (paymentPlan == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "根据内部交易流水号未查到还款计划");
        }

        BaseResult result = paymentPlanService.modifyPaymentPlan(data.getInnerSeqNo(), PayState.PAYING,
                PayState.UNCLEAR, null, paymentPlan);
        if (!result.isSuccess()) {
            return result;
        }

        result = incomePlanService.batchModifyStateByInnerSeqNo(data.getInnerSeqNo(), IncomePlanState.PAYING,
                IncomePlanState.GOING);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.PAYBACK, this);
    }

}
