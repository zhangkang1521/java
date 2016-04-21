/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.callback;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.mail.SendMailService;

/**
 * 流标回调
 * 
 * @author segen189 2015年2月2日 下午4:39:30
 */
@Component
public class LoanCanceledCallback implements Callback<DealNotify>, InitializingBean {
    private static final Logger   log = LoggerFactory.getLogger(LoanCanceledCallback.class);
    @Resource
    private DealRecordDao         dealRecordDao;
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;

    @Resource
    private LoanService           loanService;
    @Resource
    private AccountInfoService    accountInfoService;

    @Resource
    private UserService           userService;

    @Resource
    private TransferLoanService   transferLoanService;

    @Resource
    private BuyLoanService        buyLoanService;

    @Resource
    private BuyLoanSubscribeDao   buyLoanSubscribeDao;

    @Resource
    private InvestService         investService;

    @Resource
    private InvestQueryService    investQueryService;

    @Resource
    private PaymentPlanService    paymentPlanService;

    @Resource
    private IncomePlanService     incomePlanService;
    @Resource
    private LoanDao               loanDao;
    @Resource
    private SendMailService       sendMailService;
    @Resource
    private TransferLoanDao       transferLoanDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult doCallback(DealNotify data) {
        switch (data.getState()) {
            case SUCCESS:
                return doPaidSuccess(data);
            case FAILURE:
                return doPaidFailure(data);
            default:
                return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "交易状态的值不符合预期");
        }
    }

    /**
     * 流标成功的处理
     */
    private BaseResult doPaidSuccess(DealNotify data) {

        FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findByInnerSeqNo(data.getInnerSeqNo());
        if (!fullTransferRecordDO.getFtrDealState().equals(DealState.NOCALLBACK.getState())) {
            return BaseResult.SUCCESS;
        }
        TransferLoan toModify = new TransferLoan();
        toModify.setId(fullTransferRecordDO.getFtrBidId());
        toModify.setTransferLoanState(TransferLoanState.BID_CANCELED);
        toModify.setModifytime(new Date());
        //债权标的有效投资金额->0
        toModify.setCurrentValidInvest(BigDecimal.ZERO);

        // 项目跟踪状态记录
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
        traceRecord.setCreator(0);
        traceRecord.setTransferLoanId(fullTransferRecordDO.getFtrBidId());
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.bidCanceled);
        traceRecord.setOldTransferLoanState(TransferLoanState.BID_CANCELED);
        traceRecord.setNewTransferLoanState(TransferLoanState.BID_CANCELED);
        traceRecord.setNote("转让标项目流标成功，transferLoanId=" + toModify.getId());

        BaseResult modResult = transferLoanService.modifyTransferLoanInfo(toModify, traceRecord);
        if (!modResult.isSuccess()) {
            throw new BusinessException("转让标状态修改失败");
        }
        // 4. 更新受让人的收益计划表状态，受让人的投资状态
        BaseResult newResult = investService.modifyInvestState(fullTransferRecordDO.getFtrBidId(), InvestState.PAID,
                InvestState.CANCELED);
        if (!newResult.isSuccess()) {
            log.warn(newResult.getMessage());
            throw new BusinessException("批量修改转让人的收益计划状态和投资状态失败");
        }
        return BaseResult.SUCCESS;
    }

    /**
     * 流标失败的处理
     */
    private BaseResult doPaidFailure(DealNotify data) {

        FullTransferRecordDO fullTransferRecordDO = fullTransferRecordDao.findByInnerSeqNo(data.getInnerSeqNo());
        if (!fullTransferRecordDO.getFtrDealState().equals(DealState.NOCALLBACK.getState())) {
            return BaseResult.SUCCESS;
        }

//       TransferLoan toModify = new TransferLoan();
//       toModify.setId(fullTransferRecordDO.getFtrBidId());
//       toModify.setTransferLoanState(TransferLoanState.BID_CANCELED);
//        toModify.setFullTransferedtime(new Date());

        // 项目跟踪状态记录
        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();
        traceRecord.setCreator(0);
        traceRecord.setLoanId(fullTransferRecordDO.getFtrOriginId());
        traceRecord.setTransferLoanId(fullTransferRecordDO.getFtrBidId());
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.moneyTransferSucceed);
        traceRecord.setOldTransferLoanState(TransferLoanState.BID_CANCELED);
        traceRecord.setNewTransferLoanState(TransferLoanState.BID_CANCELED);
        traceRecord.setNote("转让标项目流标失败，transferLoanId=" + fullTransferRecordDO.getFtrBidId());

//        BaseResult modResult = transferLoanService.modifyTransferLoanInfo(null, traceRecord);
//        if (!modResult.isSuccess()) {
//            throw new BusinessException("转让标状态修改失败");
//        }
//        BaseResult newResult = investService.modifyInvestState(fullTransferRecordDO.getFtrId(), InvestState.PAID,
//                InvestState.CANCELED);
//        if (!newResult.isSuccess()) {
//            log.warn(newResult.getMessage());
//            throw new BusinessException("批量修改转让人的收益计划状态和投资状态失败");
//        }
        return BaseResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CashCallBackCenter.registCallBack(DealType.ABORT_BID, this);
    }

}
