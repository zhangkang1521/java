/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.LoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 普通标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:13:21
 */
@Service
public class LoanManageServiceImpl implements LoanManageService {
    private static final Logger  log = LoggerFactory.getLogger(LoanManageServiceImpl.class);

    @Resource
    private LoanDao              loanDao;

    @Resource
    private LoanService          loanService;

    @Resource
    private InvestDao            investDao;

    @Resource
    private TraceRecordDao       traceRecordDao;

    @Resource
    private ReviewService        reviewService;

    @Resource
    private DealRecordService    dealRecordService;

    @Resource
    private Callback<DealNotify> loanCanceledCallback;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult revokeToWaitProjectReview(int loanId, int operatorId, String note) {
        // 从待发布状态有条件地撤回到待项目初审
        LoanTraceRecord param = new LoanTraceRecord();
        param.setLoanId(loanId);
        param.setOldLoanState(LoanState.WAIT_RELEASE);
        param.setNewLoanState(LoanState.BID_INVITING);
        TraceRecordDO traceRecordDO = traceRecordDao.findOneByParam(LoanTraceRecordConverter.toTraceRecordDO(param));

        if (traceRecordDO != null) {
            BaseResult result = new BaseResult();
            return result.setError(CommonResultCode.BIZ_ERROR, "此时项目的状态不允许撤回");
        }
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.revokeToWaitProjectReview);
        traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        //将review的状态变更为待项目初审
        BaseResult reviewResult = reviewService.updateEndAndState(loanId, ReviewType.LOAN_FIRST_REVIEW);
        if (!reviewResult.isSuccess()) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "项目撤回失败！");
        }
        return changeResult;
    }

    @Override
    public BaseResult sendBackToWaitProjectReview(int loanId, int operatorId, String note) {
        // 把项目状态从待发布退回到待项目初审
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.sendBackToWaitProjectReview);
        traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        // 将review的状态变更为待项目初审
        BaseResult reviewResult = reviewService.updateEndAndState(loanId, ReviewType.LOAN_FIRST_REVIEW);
        if (!reviewResult.isSuccess()) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "项目回失败！");
        }
        return changeResult;
    }

    @Override
    public BaseResult sendToWaitRelease(int loanId, int operatorId, String note) {
        // 把项目状态从项目初审通过改为待发布
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.sendToWaitRelease);
        traceRecord.setOldLoanState(LoanState.PROJECT_REVIEW_PASS);
        traceRecord.setNewLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        return changeResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult removeProject(int loanId, int operatorId, String note) {
        // 融资维护项目删除
        BaseResult result = new BaseResult();

        LoanTraceRecord param = new LoanTraceRecord();
        param.setLoanId(loanId);
        param.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
        param.setNewLoanState(LoanState.BID_INVITING);

        TraceRecordDO traceRecordDO = traceRecordDao.findOneByParam(LoanTraceRecordConverter.toTraceRecordDO(param));
        if (traceRecordDO != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "此时项目的状态不允许删除");
        }

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.removeProject);
        traceRecord.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
        traceRecord.setNewLoanState(LoanState.DELETED);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            throw new BusinessException("融资维护项目删除失败");
        }

        return changeResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult forceLoanToFull(int loanId, int operatorId, String note) {
        // 普通标强制满标
        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanState(LoanState.FULL_WAIT_REVIEW);
        loan.setLoanInvestFulltime(new Date());

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.forceLoanToFull);
        traceRecord.setOldLoanState(LoanState.BID_INVITING);
        traceRecord.setNewLoanState(LoanState.FULL_WAIT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.modifyLoanInfo(loan, traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("普通标强制满标失败！{}", changeResult.getMessage());
            throw new BusinessException("普通标强制满标失败");
        }

        // 发起审核流程
        Review review = new Review();
        review.setApplyId(loanId);
        review.setType(ReviewType.LOAN_FULL_BID_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            log.error("发起项目满标审核失败！LoanId={}", loanId);
            throw new BusinessException("发起项目满标审核失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult cancelLoan(int loanId, int operatorId, String note) {
        // 流标 解冻金额
        // 获取所有投资的流水号集合
        InvestDO investParam = new InvestDO();
        investParam.setInBidId(loanId);
        investParam.setInBidType(BidType.COMMON_LOAN.getType());
        investParam.setInInvestState(InvestState.PAID.getState());
        List<InvestDO> investDOList = investDao.findListByParam(investParam, null);
        if (CollectionUtils.isEmpty(investDOList)) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资列表查询为空");
        }

        List<String> seqNos = new ArrayList<String>();
        for (InvestDO investDO : investDOList) {
            seqNos.add(investDO.getInInnerSeqNo());
        }

        // 根据流水号集合查询交易记录
        ListResult<DealRecord> oldDealResult = dealRecordService.queryDealRecordsByInnerSeqNo(seqNos);
        if (!oldDealResult.isSuccess() || CollectionUtils.isEmpty(oldDealResult.getData())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资交易记录查询失败");
        }

        // 根据查询出来的交易记录，创建新的交易记录
        Deal deal = new Deal();

        List<DealDetail> dealDetailList = new ArrayList<DealDetail>(oldDealResult.getCount());
        for (DealRecord dealRecord : oldDealResult.getData()) {
            DealDetail dealDetail = new DealDetail();

            dealDetail.setMoneyAmount(dealRecord.getMoneyAmount());
            dealDetail.setPayAccountId(dealRecord.getPayAccount());
            dealDetail.setReceiveAccountId(dealRecord.getReceiveAccount());
            dealDetail.setDealDetailType(DealDetailType.ABORT_BID_MONEY);

            dealDetailList.add(dealDetail);
        }

        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setBusinessType(DealType.ABORT_BID);
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);
        deal.setBusinessId(loanId);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, loanCanceledCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 项目跟踪记录
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelLoan);
        traceRecord.setOldLoanState(LoanState.BID_INVITING);
        traceRecord.setNewLoanState(LoanState.BID_CANCELED);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "项目状态更改失败");
        }

        // 执行新的交易
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "发起流标交易失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult releaseLoan(int loanId, int operatorId, String note) {
        BaseResult result = new BaseResult();
        // 发布普通标
        LoanDO loan = loanDao.findByLoanIdWithLock(loanId);
        if (loan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标不存在");
        }
        Loan pojo = new Loan();
        pojo.setLoanId(loanId);
        if (loan.getLoanInvestStarttime() == null) {
            pojo.setLoanInvestStarttime(new Date());
        }
        pojo.setLoanState(LoanState.BID_INVITING);

        LoanTraceRecord traceRecord = new LoanTraceRecord();
        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.releaseLoan);
        traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNewLoanState(LoanState.BID_INVITING);
        traceRecord.setNote(note);

        result = loanService.modifyLoanInfo(pojo, traceRecord);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult cancelRelease(int loanId, int operatorId, String note) {
        // 取消发布普通标
        BaseResult result = new BaseResult();

        LoanDO loan = loanDao.findByLoanIdWithLock(loanId);
        if (loan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标不存在");
        }

        // 注入的其他dao，仅仅可以用来做查询使用
        // investDao.findByParam 如果查出来有纪录，则说明有人投资了，应不予取消发布
        InvestDO pojo = new InvestDO();
        pojo.setInBidId(loanId);

        if (investDao.findByParam(pojo) != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标已存在投资纪录，不予撤销发布");
        }

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelRelease);
        traceRecord.setOldLoanState(LoanState.BID_INVITING);
        traceRecord.setNewLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        return changeResult;
    }

}
