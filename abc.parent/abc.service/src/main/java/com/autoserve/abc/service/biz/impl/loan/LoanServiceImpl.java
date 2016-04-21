/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.BorrowStatistics;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.dao.intf.GovernmentDao;
import com.autoserve.abc.dao.intf.LoanCarDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.LoanHouseDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.convert.LoanCarConverter;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.convert.LoanHouseConverter;
import com.autoserve.abc.service.biz.convert.LoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 普通标服务的实现
 * 
 * @author segen189 2014年11月17日 下午20:10:04
 */
@Service
public class LoanServiceImpl implements LoanService {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanServiceImpl.class);

	@Resource
	private LoanDao loanDao;

	@Resource
	private GovernmentDao governmentDao;

	@Resource
	private TraceRecordDao traceRecordDao;

	@Resource
	private FileUploadInfoService uploadInfoService;

	@Resource
	private InvestQueryService investQueryService;

	@Resource
	private NumberRuleService numberRuleService;

	@Resource
	private UserService userService;

	@Resource
	private GovernmentService governmentService;

	@Resource
	private ReviewService reviewService;
	@Resource
	private LoanHouseDao loanHouseDao;
	@Resource
	private LoanCarDao loanCarDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlainResult<Integer> createLoan(Loan pojo) {
		PlainResult<Integer> result = new PlainResult<Integer>();

		// 参数校验
		if (pojo.getLoanMoney() == null
				|| pojo.getLoanMoney().compareTo(BigDecimal.ZERO) <= 0) {
			return result.setError(CommonResultCode.BIZ_ERROR, "借款金额不合法");
		}

		// 查询前台用户类型
		pojo.setLoaneeType(queryUserTypeByUserId(pojo.getLoanUserId()));

		// 借款人只可是前台用户
		if (!LoaneeType.PERSON.equals(pojo.getLoaneeType())
				&& !LoaneeType.COMPANY.equals(pojo.getLoaneeType())) {
			return result.setError(CommonResultCode.BIZ_ERROR, "借款人只可是前台用户");
		}

		// 检查个人可用信用额度，通过则减少可用额度
		BaseResult userCreditModResult = userService.reduceSettCredit(
				pojo.getLoanUserId(), pojo.getLoanMoney());
		if (!userCreditModResult.isSuccess()) {
			throw new BusinessException(userCreditModResult.getMessage());
		}

		// 检查担保公司可用额度，通过则减少可用额度
		if (pojo.getLoanGuarGov() != null) {
			// BaseResult govModResult =
			// governmentService.computeSettGuarAmount(pojo.getLoanGuarGov(),
			// pojo.getLoanMoney()
			// .negate());
			// if (!govModResult.isSuccess()) {
			// throw new BusinessException("担保公司可用额度减少失败");
			// }

			GovernmentDO govDO = governmentDao.findByIdWithLock(pojo
					.getLoanGuarGov());
			if (govDO == null) {
				throw new BusinessException("担保公司不存在");
			} else if (pojo.getLoanMoney().compareTo(
					govDO.getGovMaxGuarAmount()) > 0) {
				throw new BusinessException("借款额度不能大于担保公司的最大担保额度");
			}
		}

		// 创建普通标
		// pojo.setLoanState(LoanState.WAIT_PROJECT_REVIEW);
		LoanDO loanDO = LoanConverter.toLoanDO(pojo);
		loanDao.insert(loanDO);

		// // 更新项目编号（暂不启用）
		// PlainResult<String> loanNumberResult =
		// numberRuleService.createNumberByNumberRule(loanDO.getLoanId());
		// if (!loanNumberResult.isSuccess()) {
		// throw new BusinessException("项目编号创建失败");
		// }
		//
		// LoanDO toModify = new LoanDO();
		// toModify.setLoanNo(loanNumberResult.getData());
		// toModify.setLoanId(loanDO.getLoanId());
		// loanDao.update(toModify);

		BaseResult initiateResult = initiateFirstReview(loanDO.getLoanId());
		if (!initiateResult.isSuccess()) {
			throw new BusinessException("发起审核流程失败");
		}

		result.setData(loanDO.getLoanId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult modifyLoanInfo(Loan pojo, LoanTraceRecord traceRecord) {
		// 参数校验
		if (pojo == null || pojo.getLoanId() == null) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		} else if ((pojo.getLoanState() != null)
				&& (traceRecord == null || !pojo.getLoanState().equals(
						traceRecord.getNewLoanState()))) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		} else if (pojo.getLoanMoney() != null
				&& pojo.getLoanMoney().compareTo(BigDecimal.ZERO) <= 0) {
			return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
					"借款金额不合法");
		}

		LoanDO loanDO = loanDao.findByLoanIdWithLock(pojo.getLoanId());
		if (loanDO == null) {
			return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
					"要修改的普通标不存在");
		}

		// 如果要修改借款金额，需检查个人可用信用额度，通过则减少可用额度
		if (pojo.getLoanMoney() != null
				&& pojo.getLoanMoney() != loanDO.getLoanMoney()) {
			BigDecimal money = loanDO.getLoanMoney().subtract(
					pojo.getLoanMoney());
			BaseResult userCreditModResult = userService.reduceSettCredit(
					pojo.getLoanUserId(), money);
			if (!userCreditModResult.isSuccess()) {
				throw new BusinessException("借款人可用信用额度减少失败");
			}
		}

		// 如果要修改担保公司，需检查担保公司可用额度，通过则减少可用额度，增加原担保公司可用额度
		if (pojo.getLoanGuarGov() != null) {

			if (loanDO.getLoanGuarGov() == null) {
				loanDO.setLoanGuarGov(pojo.getLoanGuarGov());
			}
			BaseResult oldGuarModResult = governmentService
					.computeSettGuarAmount(loanDO.getLoanGuarGov(),
							loanDO.getLoanMoney());
			if (!oldGuarModResult.isSuccess()) {
				throw new BusinessException("原担保公司可用额度增加失败");
			}

			BaseResult newGuarModResult = governmentService
					.computeSettGuarAmount(pojo.getLoanGuarGov(), pojo
							.getLoanMoney() != null ? pojo.getLoanMoney()
							.negate() : loanDO.getLoanMoney().negate());
			if (!newGuarModResult.isSuccess()) {
				throw new BusinessException("担保公司可用额度减少失败");
			}

		}
		// 如果要修改借款状态，必要时需增加个人可用信用额度以及担保公司可用额度
		else if (pojo.getLoanState() != null
				&& pojo.getLoanState().isEndedState()) {
			// 增加个人可用信用额度
			BaseResult userCreditModResult = userService.addSettCredit(
					loanDO.getLoanUserId(), loanDO.getLoanMoney());
			if (!userCreditModResult.isSuccess()) {
				throw new BusinessException("借款人可用信用额度增加失败");
			}

			// 增加担保公司可用额度
			// if (loanDO.getLoanGuarGov() != null) {
			// BaseResult newGuarModResult =
			// governmentService.computeSettGuarAmount(loanDO.getLoanGuarGov(),
			// pojo.getLoanMoney() != null ? pojo.getLoanMoney() :
			// loanDO.getLoanMoney());
			// if (!newGuarModResult.isSuccess()) {
			// throw new BusinessException("担保公司可用额度增加失败");
			// }
			// }
		}

		// 添加项目跟踪状态记录
		if (traceRecord != null) {
			int count = traceRecordDao.insert(LoanTraceRecordConverter
					.toTraceRecordDO(traceRecord));
			if (count <= 0) {
				return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
						"项目跟踪状态记录创建失败");
			}
		}

		// 编辑项目信息
		loanDao.update(LoanConverter.toLoanDO(pojo));

		return BaseResult.SUCCESS;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult infoSupplementForLoan(Loan loan,
			LoanTraceRecord traceRecord) {
		if (loan == null) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		} else if (loan.getLoanState() != null && traceRecord == null) {
			return new BaseResult().setError(CommonResultCode.ILEEGAL_ARGUMENT);
		}

		// 只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全
		LoanDO loanDO = loanDao.findById(loan.getLoanId());
		if (loanDO == null) {
			return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
					"项目不存在");
		} else if (loanDO.getLoanState().equals(
				LoanState.WAIT_MAINTAIN_REVIEW.getState())) {
			// PASS
		} else if (loanDO.getLoanState().equals(
				LoanState.WAIT_PROJECT_REVIEW.getState())) {
			// PASS
		} else if (loanDO.getLoanState().equals(
				LoanState.PROJECT_REVIEW_BACK.getState())) {
			// PASS
		} else {
			return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
					"只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全");
		}

		// 融资申请资料补全，更新审核的version
		BaseResult reviewUpdateResult = reviewService.updateVersion(
				ReviewType.FINANCING_REVIEW, loan.getLoanId());
		if (!reviewUpdateResult.isSuccess()) {
			throw new BusinessException("更新审核的version失败");
		}

		// 编辑项目信息
		return modifyLoanInfo(loan, traceRecord);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult changeLoanState(LoanTraceRecord traceRecord) {
		BaseResult result = new BaseResult();

		// 添加项目跟踪状态记录
		int count = traceRecordDao.insert(LoanTraceRecordConverter
				.toTraceRecordDO(traceRecord));
		if (count <= 0) {
			return result.setError(CommonResultCode.BIZ_ERROR, "项目跟踪状态记录创建失败");
		}

		// 如果要修改借款状态，必要时需增加个人可用信用额度以及担保公司可用额度
		if (traceRecord.getNewLoanState() != null
				&& traceRecord.getNewLoanState().isEndedState()) {
			LoanDO loanDO = loanDao.findByLoanIdWithLock(traceRecord
					.getLoanId());
			if (loanDO == null) {
				return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
						"要修改的普通标不存在");
			}

			// 增加个人可用信用额度
			BaseResult userCreditModResult = userService.addSettCredit(
					loanDO.getLoanUserId(), loanDO.getLoanMoney());
			if (!userCreditModResult.isSuccess()) {
				throw new BusinessException("借款人可用信用额度增加失败");
			}

			// 增加担保公司可用额度
			// if (loanDO.getLoanGuarGov() != null) {
			// BaseResult newGuarModResult =
			// governmentService.computeSettGuarAmount(loanDO.getLoanGuarGov(),
			// loanDO.getLoanMoney());
			// if (!newGuarModResult.isSuccess()) {
			// throw new BusinessException("担保公司可用额度增加失败");
			// }
			// }
		}

		// 更改项目状态
		count = loanDao.updateState(traceRecord.getLoanId(), traceRecord
				.getOldLoanState() == null ? null : traceRecord
				.getOldLoanState().getState(), traceRecord.getNewLoanState()
				.getState(), traceRecord.getCreator());
		if (count <= 0) {
			return result.setError(CommonResultCode.BIZ_ERROR, "项目状态改变失败");
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult initiateFirstReview(int loanId) {
		BaseResult result = new BaseResult();

		Review review = new Review();
		review.setApplyId(loanId);
		review.setType(ReviewType.FINANCING_REVIEW);
		review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);

		BaseResult reviewRes = reviewService.initiateReview(review);
		if (!reviewRes.isSuccess()) {
			logger.error("发起项目初审失败！LoanId={}", loanId);
			return result.setError(CommonResultCode.BIZ_ERROR, "发起项目初审失败");
		}

		return result;
	}

	private LoaneeType queryUserTypeByUserId(int userId) {
		PlainResult<User> userResult = userService.findEntityById(userId);
		if (!userResult.isSuccess() || userResult.getData() == null) {
			throw new BusinessException("用户类型查询失败");
		}

		UserType userType = userResult.getData().getUserType();
		switch (userType) {
		case PERSONAL:
			return LoaneeType.PERSON;
		case ENTERPRISE:
			return LoaneeType.COMPANY;
		case PARTNER:
			throw new BusinessException("暂不支持后台用户借款");
		default:
			return null;
		}
	}

	@Override
	public ListResult<Loan> queryByIds(List<Integer> ids,
			InvestSearchJDO investSearchJDO) {
		ListResult<Loan> result = new ListResult<Loan>();

		if (CollectionUtils.isEmpty(ids)) {
			return result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT,
					"loanIds");
		}

		List<LoanDO> loanDOList = loanDao.findByListAndSrearch(ids,
				investSearchJDO);

		if (CollectionUtils.isEmpty(loanDOList)) {
			return result.setErrorMessage(
					CommonResultCode.ERROR_DATA_NOT_EXISTS, "loanIds");
		}

		result.setData(LoanConverter.toLoanList(loanDOList));
		return result;
	}

	@Override
	public PlainResult<BigDecimal> queryTotal() {
		PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
		result.setData(this.loanDao.findTotal());
		return result;
	}

	@Override
	public PageResult<MyBorrowTender> queryMyBorrowTender(
			InvestSearchJDO searchDO, PageCondition pageCondition) {
		PageResult<MyBorrowTender> result = new PageResult<MyBorrowTender>(
				pageCondition);
		int count = this.loanDao.countMyBorrowTender(searchDO);
		if (count > 0) {
			List<MyBorrowTender> list = this.loanDao.findMyBorrowTender(
					searchDO, pageCondition);
			for (MyBorrowTender borrow : list) {
				if (borrow.getState() == 10 || borrow.getState() == 11
						|| borrow.getState() == 14 || borrow.getState() == 15) {
					borrow.setInvestProgress(100);
				}else{
					borrow.setInvestProgress(Arith.calcPercent(
							borrow.getInvestMoney(), borrow.getLoanMoney())
							.intValue());
				}
			}
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	public PageResult<MyBorrowReceived> queryMyBorrowReceived(
			InvestSearchJDO searchDO, PageCondition pageCondition) {
		PageResult<MyBorrowReceived> result = new PageResult<MyBorrowReceived>(
				pageCondition);
		int count = this.loanDao.countMyBorrowReceived(searchDO);
		if (count > 0) {
			List<MyBorrowReceived> list = this.loanDao.findMyBorrowReceived(
					searchDO, pageCondition);
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	public PageResult<MyBorrowClean> queryMyBorrowClean(
			InvestSearchJDO searchDO, PageCondition pageCondition) {
		PageResult<MyBorrowClean> result = new PageResult<MyBorrowClean>(
				pageCondition);
		int count = this.loanDao.countMyBorrowClean(searchDO);
		if (count > 0) {
			List<MyBorrowClean> list = this.loanDao.findMyBorrowClean(searchDO,
					pageCondition);
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	public PageResult<BorrowStatistics> queryBorrowStatistics(Integer userId,
			PageCondition pageCondition) {
		PageResult<BorrowStatistics> result = new PageResult<BorrowStatistics>(
				pageCondition);
		int count = this.loanDao.countBorrowStatistics(userId);
		if (count > 0) {
			List<BorrowStatistics> list = this.loanDao.findBorrowStatistics(
					userId, pageCondition);
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlainResult<Integer> createCustomLoan(Loan loan, JSONArray list) {
		try {

			List<LoanCar> loanCarList = new ArrayList<LoanCar>();

			List<LoanHouse> loanHouseList = new ArrayList<LoanHouse>();

			PlainResult<Integer> basicLoanResult = this.createLoan(loan);
			if (!basicLoanResult.isSuccess()) {
				throw new BusinessException("项目基本信息创建失败");
			}
			for (Object json : list) {
				JSONObject formJson = (JSONObject) json;
				LoanCategory loanCategory = LoanCategory.valueOf(formJson
						.getInteger("loanType"));
				if (loanCategory != null)
					switch (loanCategory) {
					case LOAN_CAR: {
						LoanCar loanCar = assembleLoanC(formJson);
						loanCar.setLoanId(basicLoanResult.getData());
						loanCarList.add(loanCar);
						break;
					}
					case LOAN_HOUSE: {
						LoanHouse loanHouse = assembleLoanH(formJson);
						loanHouse.setLoanId(basicLoanResult.getData());
						loanHouseList.add(loanHouse);
						break;
					}
					default: {
					}
					}

			}
			if (loanCarList.size() > 0) {
				this.loanCarDao.batchInsert(LoanCarConverter
						.toLoanCarDOList(loanCarList));
			}
			if (loanHouseList.size() > 0) {
				this.loanHouseDao.batchInsert(LoanHouseConverter
						.toLoanHouseDOList(loanHouseList));
			}
			return basicLoanResult;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult infoSupplementForCustomLoan(Loan pojo, JSONArray list) {

		BaseResult result;

		try {
			result = new BaseResult();
			List<LoanCar> loanCarList = new ArrayList<LoanCar>();
			List<LoanHouse> loanHouseList = new ArrayList<LoanHouse>();
			if (pojo == null || pojo.getLoanId() <= 0) {
				throw new BusinessException("项目id不能为空");
			}
			// if (CollectionUtils.isEmpty(list)) {
			// return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
			// }
			// 只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全
			LoanDO loanDO = loanDao.findById(pojo.getLoanId());
			if (loanDO == null) {
				return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
						"项目不存在");
			} else if (loanDO.getLoanState().equals(
					LoanState.WAIT_MAINTAIN_REVIEW.getState())) {
				// PASS
			} else if (loanDO.getLoanState().equals(
					LoanState.WAIT_PROJECT_REVIEW.getState())) {
				// PASS
			} else if (loanDO.getLoanState().equals(
					LoanState.PROJECT_REVIEW_BACK.getState())) {
				// PASS
			} else {
				return new BaseResult().setError(CommonResultCode.BIZ_ERROR,
						"只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全");
			}
			// 融资申请资料补全，更新审核的version
			BaseResult reviewUpdateResult = reviewService.updateVersion(
					ReviewType.FINANCING_REVIEW, pojo.getLoanId());
			if (!reviewUpdateResult.isSuccess()) {
				throw new BusinessException("更新审核的version失败");
			}
			// 发起项目基本信息
			this.modifyLoanInfo(pojo, null);
			loanCarDao.updateDeletedByLoanId(pojo.getLoanId(), true);
			loanHouseDao.updateDeletedByLoanId(pojo.getLoanId(), true);
			for (Object json : list) {
				JSONObject formJson = (JSONObject) json;
				LoanCategory loanCategory = LoanCategory.valueOf(formJson
						.getInteger("loanType"));
				switch (loanCategory) {
				case LOAN_CAR: {
					// 修改汽车信息
					LoanCar loanCar = assembleLoanC(formJson);
					loanCar.setLoanId(pojo.getLoanId());
					loanCarList.add(loanCar);
					break;
				}
				case LOAN_HOUSE: {
					// 修改房屋信息
					LoanHouse loanHouse = assembleLoanH(formJson);
					loanHouse.setLoanId(pojo.getLoanId());
					loanHouseList.add(loanHouse);
					break;
				}
				default: {
				}
				}
			}
			if (loanHouseList.size() > 0) {
				this.loanHouseDao.batchInsert(LoanHouseConverter
						.toLoanHouseDOList(loanHouseList));
			}
			if (loanCarList.size() > 0) {
				this.loanCarDao.batchInsert(LoanCarConverter
						.toLoanCarDOList(loanCarList));
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException("数据不合法");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public BaseResult modifyCustomLoan(Loan pojo, JSONArray list) {

		BaseResult result;

		try {
			result = new BaseResult();
			List<LoanCar> loanCarList = new ArrayList<LoanCar>();
			List<LoanHouse> loanHouseList = new ArrayList<LoanHouse>();
			if (pojo == null || pojo.getLoanId() <= 0) {
				throw new BusinessException("项目id不能为空");
			}
			// if (CollectionUtils.isEmpty(list)) {
			// return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
			// }
			// 修改项目基本信息
			BaseResult basicLoanResult = this.modifyLoanInfo(pojo, null);
			if (!basicLoanResult.isSuccess()) {
				throw new BusinessException("项目基本信息修改失败");
			}

			loanCarDao.updateDeletedByLoanId(pojo.getLoanId(), true);
			loanHouseDao.updateDeletedByLoanId(pojo.getLoanId(), true);

			for (Object json : list) {
				JSONObject formJson = (JSONObject) json;
				LoanCategory loanCategory = LoanCategory.valueOf(formJson
						.getInteger("loanType"));
				switch (loanCategory) {
				case LOAN_CAR: {
					// 修改汽车信息
					LoanCar loanCar = assembleLoanC(formJson);
					loanCar.setLoanId(pojo.getLoanId());
					loanCarList.add(loanCar);
					break;
				}
				case LOAN_HOUSE: {
					// 修改房屋信息
					LoanHouse loanHouse = assembleLoanH(formJson);
					loanHouse.setLoanId(pojo.getLoanId());
					loanHouseList.add(loanHouse);
					break;
				}
				default: {
				}
				}
			}
			if (loanHouseList.size() > 0) {
				this.loanHouseDao.batchInsert(LoanHouseConverter
						.toLoanHouseDOList(loanHouseList));
			}
			if (loanCarList.size() > 0) {
				this.loanCarDao.batchInsert(LoanCarConverter
						.toLoanCarDOList(loanCarList));
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException("汽车日期转换异常");
		}
	}

	private LoanCar assembleLoanC(JSONObject formJson) throws Exception {
		LoanCar loanCar = new LoanCar();

		if (StringUtils.isNotBlank(formJson.getString("pro_car_year"))) {
			loanCar.setTime(DateUtil.parseDate(
					formJson.getString("pro_car_year"),
					DateUtil.DEFAULT_DAY_STYLE));
		}
		loanCar.setBrand(formJson.getString("pro_car_brand"));
		loanCar.setSeries(formJson.getString("pro_car_type"));
		loanCar.setOutput(formJson.getString("pro_car_output"));
		loanCar.setColor(formJson.getString("pro_car_color"));
		if (StringUtils.isNotBlank(formJson.getString("pro_buy_year"))) {
			loanCar.setBuyYear(Integer.parseInt(formJson
					.getString("pro_buy_year")));
		}
		loanCar.setRun(StringUtils.isBlank(formJson.getString("pro_car_run")) ? null
				: new BigDecimal(formJson.getString("pro_car_run")));
		loanCar.setAssessMoney(StringUtils.isBlank(formJson
				.getString("pro_assess_money")) ? null : new BigDecimal(
				formJson.getString("pro_assess_money")));
		loanCar.setCarAddress(formJson.getString("pro_car_address"));

		return loanCar;
	}

	private LoanHouse assembleLoanH(JSONObject formJson) throws Exception {

		LoanHouse loanHouse = new LoanHouse();

		loanHouse.setHouseMeasure(StringUtils.isBlank(formJson
				.getString("pro_house_measure")) ? null : new BigDecimal(
				formJson.getString("pro_house_measure")));
		loanHouse.setCoverMeasure(StringUtils.isBlank(formJson
				.getString("pro_cover_measure")) ? null : new BigDecimal(
				formJson.getString("pro_cover_measure")));
		loanHouse.setHouseNo(formJson.getString("pro_house_no"));
		loanHouse.setHouseArea(formJson.getString("pro_house_area"));
		loanHouse.setHouseAge(StringUtils.isBlank(formJson
				.getString("pro_house_age")) ? null : new BigDecimal(formJson
				.getString("pro_house_age")));
		loanHouse.setIsMortgage("true".equals(formJson
				.getString("pro_house_mortgage")));
		loanHouse.setAssessMoney(StringUtils.isBlank(formJson
				.getString("pro_assess_money")) ? null : new BigDecimal(
				formJson.getString("pro_assess_money")));

		return loanHouse;
	}

	@Override
	public BaseResult updateContractPath(Integer loanId, String contractPath) {
		BaseResult result = new BaseResult();
		if (loanDao.updateContractPath(loanId, contractPath) > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}

}
