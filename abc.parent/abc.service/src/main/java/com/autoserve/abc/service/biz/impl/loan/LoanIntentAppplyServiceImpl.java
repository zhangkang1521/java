/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.LoanIntentApplyDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.LoanIntentAppplyDao;
import com.autoserve.abc.service.biz.convert.LoanIntentApplyConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.loan.LoanCarService;
import com.autoserve.abc.service.biz.intf.loan.LoanHouseService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.UuidUtil;

/**
 * 意向申请服务
 * 
 * @author wuqiang.du 2014年12月22日 下午1:27:30
 * @author yuqing.zheng
 */
@Service
public class LoanIntentAppplyServiceImpl implements LoanIntentApplyService {

    private static final Logger logger = LoggerFactory.getLogger(LoanIntentAppplyServiceImpl.class);

    @Resource
    private LoanIntentAppplyDao loanIntentAppplyDao;

    @Resource
    private LoanDao             loanDao;

    @Resource
    private LoanService         loanService;

    @Resource
    private LoanCarService      loanCarService;

    @Resource
    private LoanHouseService    loanHouseService;

    @Resource
    private UserService         userService;

    @Resource
    private ReviewService       reviewService;

    @Override
    public PlainResult<LoanIntentApply> queryById(int id) {
        PlainResult<LoanIntentApply> result = new PlainResult<LoanIntentApply>();

        if (id <= 0) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        LoanIntentApplyDO intentApplyDO = loanIntentAppplyDao.findById(id);
        if (intentApplyDO == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
        }

        LoanIntentApply intentApply = LoanIntentApplyConverter.toLoanIntentApply(intentApplyDO);
        result.setData(intentApply);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult createLoanIntentApply(LoanIntentApply pojo) {
        // 设置用户类型、用户名称
        PlainResult<User> userResult = userService.findEntityById(pojo.getUserId());
        if (!userResult.isSuccess() || userResult.getData() == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "用户类型查询失败");
        }

        LoaneeType intenteeType = null;
        if (UserType.PERSONAL.equals(userResult.getData().getUserType())) {
            intenteeType = LoaneeType.PERSON;
        } else if (UserType.ENTERPRISE.equals(userResult.getData().getUserType())) {
            intenteeType = LoaneeType.COMPANY;
        }

        pojo.setIntenteeType(intenteeType);
        pojo.setUserName(userResult.getData().getUserName());

        // 设置附件url uuid
        pojo.setFileUrl(UuidUtil.generateUuid());

        // 设置审核状态为意向待审核
        pojo.setIntentState(LoanState.WAIT_INTENT_REVIEW);

        LoanIntentApplyDO intentApplyDO = LoanIntentApplyConverter.toLoanIntentApplyDO(pojo);
        loanIntentAppplyDao.insert(intentApplyDO);

        // 创建意向成功后发起意向审核
        Review review = new Review();
        review.setType(ReviewType.INTENTION_REVIEW);
        review.setApplyId(intentApplyDO.getLiaId());
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("发起意向审核失败！LoanIntentApplyId={}", intentApplyDO.getLiaId());
            throw new BusinessException("发起意向审核失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult modifyApplyLoanIntent(LoanIntentApply pojo) {
        loanIntentAppplyDao.update(LoanIntentApplyConverter.toLoanIntentApplyDO(pojo));
        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PlainResult<Integer> infoSupplementForLoan(int loanIntentId, Loan loan) {

        if (loanIntentId <= 0 || loan == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        //置位
        loan.setLoanIntentId(loanIntentId);

        LoanDO loanDO = loanDao.findByIntentIdWithLock(loanIntentId);

        BaseResult reviewUpdateResult = reviewService.updateVersion(ReviewType.INTENTION_REVIEW, loanIntentId);
        if (!reviewUpdateResult.isSuccess()) {
            throw new BusinessException("更新审核的version失败");
        }

        if (loanDO == null) {
            // 来源于前台意向
            loan.setLoanFromIntent(true);
            loan.setLoanState(LoanState.WAIT_INTENT_REVIEW);

            PlainResult<Integer> rawResult = loanService.createLoan(loan);
            if (!rawResult.isSuccess()) {
                throw new BusinessException("创建项目信息失败");
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaLoanId(rawResult.getData());
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);

            return rawResult;
        } else {
            BaseResult updateResult = loanService.modifyLoanInfo(loan, null);
            if (!updateResult.isSuccess()) {
                throw new BusinessException("更新项目信息失败");
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);
            
            PlainResult<Integer> result = new PlainResult<Integer>();
            result.setData(loanDO.getLoanId());
            result.setSuccess(updateResult.isSuccess());
            result.setMessage(updateResult.getMessage());

            return result;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PlainResult<Integer> infoSupplementForHouseLoan(int loanIntentId, Loan loan, List<LoanHouse> houseList) {

        if (loanIntentId <= 0 || loan == null || CollectionUtils.isEmpty(houseList)) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        //置位
        loan.setLoanIntentId(loanIntentId);

        LoanDO loanDO = loanDao.findByIntentIdWithLock(loanIntentId);

        BaseResult reviewUpdateResult = reviewService.updateVersion(ReviewType.INTENTION_REVIEW, loanIntentId);
        if (!reviewUpdateResult.isSuccess()) {
            throw new BusinessException("更新审核的version失败");
        }

        if (loanDO == null) {
            // 来源于前台意向
            loan.setLoanFromIntent(true);
            loan.setLoanState(LoanState.WAIT_INTENT_REVIEW);

            PlainResult<Integer> loanHouseResult = loanHouseService.createHouseLoan(loan, houseList);
            if (!loanHouseResult.isSuccess()) {
                throw new BusinessException(loanHouseResult.getMessage());
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaLoanId(loanHouseResult.getData());
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);

            return loanHouseResult;
        } else {
            BaseResult updateResult = loanHouseService.modifyHouseLoan(loan, houseList);
            if (!updateResult.isSuccess()) {
                throw new BusinessException("修改房屋抵押贷信息失败");
            }

         // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);
            
            PlainResult<Integer> result = new PlainResult<Integer>();
            result.setData(loanDO.getLoanId());
            result.setSuccess(updateResult.isSuccess());
            result.setMessage(updateResult.getMessage());

            return result;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PlainResult<Integer> infoSupplementForCarLoan(int loanIntentId, Loan loan, List<LoanCar> carList) {

        if (loanIntentId <= 0 || loan == null || CollectionUtils.isEmpty(carList)) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        //置位
        loan.setLoanIntentId(loanIntentId);

        LoanDO loanDO = loanDao.findByIntentIdWithLock(loanIntentId);

        BaseResult reviewUpdateResult = reviewService.updateVersion(ReviewType.INTENTION_REVIEW, loanIntentId);
        if (!reviewUpdateResult.isSuccess()) {
            throw new BusinessException("更新审核的version失败");
        }

        if (loanDO == null) {
            // 来源于前台意向
            loan.setLoanFromIntent(true);
            loan.setLoanState(LoanState.WAIT_INTENT_REVIEW);

            PlainResult<Integer> loanCarResult = loanCarService.createCarLoan(loan, carList);
            if (!loanCarResult.isSuccess()) {
                throw new BusinessException("创建汽车抵押贷失败");
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaLoanId(loanCarResult.getData());
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);

            return loanCarResult;
        } else {
            BaseResult updateResult = loanCarService.modifyCarLoan(loan, carList);
            if (!updateResult.isSuccess()) {
                throw new BusinessException("更新汽车抵押贷信息失败");
            }

            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);
            
            PlainResult<Integer> result = new PlainResult<Integer>();
            
            result.setData(loanDO.getLoanId());
            result.setSuccess(updateResult.isSuccess());
            result.setMessage(updateResult.getMessage());

            return result;
        }
    }

    @Override
    public PageResult<LoanIntentApply> queryApplyLoanIntentListByParam(LoanIntentApply pojo, PageCondition pageCondition) {
        PageResult<LoanIntentApply> result = new PageResult<LoanIntentApply>(pageCondition.getPage(),
                pageCondition.getPageSize());

        int totalCount = loanIntentAppplyDao.countListByParam(LoanIntentApplyConverter.toLoanIntentApplyDO(pojo));
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(LoanIntentApplyConverter.toLoanIntentApplyList(loanIntentAppplyDao.findListByParam(
                    LoanIntentApplyConverter.toLoanIntentApplyDO(pojo), pageCondition)));
        }

        return result;
    }

    @Override
    public PageResult<LoanIntentApply> queryApplyLoanIntentList(PageCondition pageCondition) {
        PageResult<LoanIntentApply> result = new PageResult<LoanIntentApply>(pageCondition);

        int totalCount = loanIntentAppplyDao.countListByParam(null);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<LoanIntentApplyDO> listDO = loanIntentAppplyDao.findList(pageCondition);
            result.setData(LoanIntentApplyConverter.toLoanIntentApplyList(listDO));
        }

        return result;
    }

    @Override
    public ListResult<LoanIntentApply> queryByIntentIds(List<Integer> intentIdList) {
        ListResult<LoanIntentApply> result = new ListResult<LoanIntentApply>();

        if (CollectionUtils.isEmpty(intentIdList)) {
            logger.warn("参数校验失败，id列表为空");
            result.setError(CommonResultCode.ILEEGAL_ARGUMENT, "未找到相关意向申请");
            return result;
        }

        List<LoanIntentApplyDO> intentDOList = loanIntentAppplyDao.findByIdList(intentIdList);
        if (CollectionUtils.isEmpty(intentDOList)) {
            logger.warn("查询LoanIntentApply，结果为空");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "意向申请");
            return result;
        }

        result.setData(LoanIntentApplyConverter.toLoanIntentApplyList(intentDOList));
        return result;
    }

    @Override
    public PlainResult<Integer> countApplyLoanIntentforNow(Integer userId) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        Integer count = loanIntentAppplyDao.countByNow(userId);
        result.setData(count);
        return result;
    }

    @Override
    public BaseResult infoSupplementForCustomLoan(int loanIntentId, Loan loan, JSONArray list) {
        if (loanIntentId <= 0 || loan == null) {
            return new PlainResult<Integer>().setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        //置位
        loan.setLoanIntentId(loanIntentId);

        LoanDO loanDO = loanDao.findByIntentIdWithLock(loanIntentId);

        BaseResult reviewUpdateResult = reviewService.updateVersion(ReviewType.INTENTION_REVIEW, loanIntentId);
        if (!reviewUpdateResult.isSuccess()) {
            throw new BusinessException("更新审核的version失败");
        }

        if (loanDO == null) {
            // 来源于前台意向
            loan.setLoanFromIntent(true);
            loan.setLoanState(LoanState.WAIT_INTENT_REVIEW);

            PlainResult<Integer> loanCustomResult = loanService.createCustomLoan(loan, list);
            if (!loanCustomResult.isSuccess()) {
                throw new BusinessException("创建自定义抵押贷失败");
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaLoanId(loanCustomResult.getData());
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);

            return loanCustomResult;
        } else {
            BaseResult updateResult = loanService.modifyCustomLoan(loan, list);
            if (!updateResult.isSuccess()) {
                throw new BusinessException("更新自定义抵押贷信息失败");
            }

            // 将loanId更新到意向申请中去
            LoanIntentApplyDO toModify = new LoanIntentApplyDO();
            toModify.setLiaId(loanIntentId);
            toModify.setLiaIntentMoney(loan.getLoanMoney());
            loanIntentAppplyDao.update(toModify);
            
            PlainResult<Integer> result = new PlainResult<Integer>();
            result.setData(loanDO.getLoanId());
            result.setSuccess(updateResult.isSuccess());
            result.setMessage(updateResult.getMessage());

            return result;
        }
    }

}
