package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 项目投资
 * 
 * @author tabor
 */
public class LoanRequest {

	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private UserService userService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private InvestService investService;
	@Resource
	private DealRecordService dealRecordService;
	@Resource
	private DoubleDryService doubleDryService;
	@Autowired
	private FileUploadInfoService fileUploadInfoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");

			if ("1".equals(catalog)) {
				// 投资列表
				String loanType = params.getString("prodId");// 标的类型 1 2 3 4，信用标，担保标等，不传查询所有
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				LoanSearchDO searchDO = new LoanSearchDO();
				if (!StringUtils.isBlank(loanType)) {
					searchDO.setLoanCategory(Integer.parseInt(loanType));
				}
				searchDO.setLoanState(Arrays.asList(
						LoanState.BID_INVITING.state,
						LoanState.FULL_WAIT_REVIEW.state,
						LoanState.FULL_REVIEW_PASS.state,
						LoanState.FULL_REVIEW_FAIL.state,
						LoanState.BID_CANCELED.state,
						LoanState.MONEY_TRANSFERING.state,
						LoanState.REPAYING.state));
				PageCondition pageCondition = new PageCondition(showPage,
						pageSize);
				PageResult<Loan> pageResult = this.loanQueryService
						.queryLoanListBySearchParam(searchDO, pageCondition);
				Pagebean<Loan> pagebean = new Pagebean<Loan>(showPage,
						pageSize, pageResult.getData(),
						pageResult.getTotalCount());
				// 百分比
				Map<Integer, BigDecimal> resultLoanListMap = new HashMap<Integer, BigDecimal>();
				for (Loan temp : pagebean.getRecordList()) {
					BigDecimal percent =Arith.calcPercent(temp.getLoanCurrentInvest(), temp.getLoanMoney());
					resultLoanListMap.put(temp.getLoanId(), percent);
				}

				for (Loan loan3 : pagebean.getRecordList()) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", loan3.getLoanId());
					loanMap.put("loanTitle", loan3.getLoanNo());
					loanMap.put("loanMoney", loan3.getLoanMoney());
					loanMap.put("loanRate", loan3.getLoanRate());
					loanMap.put("loanPeriod", loan3.getLoanPeriod()
							+ loan3.getLoanPeriodUnit().getPrompt());
					loanMap.put("loanProgress", Arith.calcPercent(loan3.getLoanCurrentValidInvest(), loan3.getLoanMoney()).intValue());
					loanMap.put("loanType", loan3.getLoanCategory().category);
					loanMap.put("loanInvestEndTime", DateUtil.formatDate(loan3.getLoanInvestEndtime()));
					loanMap.put("loanExpireTime", DateUtil.formatDate(loan3.getLoanExpireDate()));
					loanList.add(loanMap);
				}
				objMap.put("pageCount", pagebean.getRecordCount());
				objMap.put("list", JSON.toJSON(loanList));
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				// 投资详情
				// /mobile/loanRequest.json?catalog=2&loanId=474
				Integer loanId = params.getInt("loanId");

				Loan loanParam = new Loan();
				loanParam.setLoanId(loanId);
				Loan loan = loanQueryService.queryByParam(loanParam).getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("loanId", loan.getLoanId());
				objMap.put("loanTitle", loan.getLoanNo());
				objMap.put("loanStartTime", new SimpleDateFormat("yyyy-MM-dd")
						.format(loan.getLoanCreatetime()));
				objMap.put("loanMoney", loan.getLoanMoney());
				objMap.put("loanRate", loan.getLoanRate());
				objMap.put("effectiveDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(loan.getLoanExpireDate()));
				objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
				objMap.put("currentInvest", loan.getLoanCurrentValidInvest());
				objMap.put(
						"currentValidInvest",
						loan.getLoanMoney().subtract(
								loan.getLoanCurrentValidInvest()));
				objMap.put("period", loan.getLoanPeriod());
				objMap.put("period_type", loan.getLoanPeriodUnit());
				objMap.put("investEndtime", DateUtil.formatDate(
						loan.getLoanInvestEndtime(), "yyyy-MM-dd"));
				objMap.put("minInvest", loan.getLoanMinInvest());
				objMap.put("maxInvest", loan.getLoanMaxInvest());
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("3".equals(catalog)) {
				// 投资记录
				Integer loanId = params.getInt("loanId");
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");

				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setOriginId(loanId);
				PageResult<Invest> pageResult = investQueryService
						.queryInvestList(searchDO, new PageCondition(showPage,
								pageSize));
				List<Invest> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				for (Invest invest : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("investor",
							userService.findEntityById(invest.getUserId())
									.getData().getUserName());
					loanMap.put("investTime", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(invest
							.getCreatetime()));
					loanMap.put("investMoney", invest.getInvestMoney());
					loanList.add(loanMap);
				}

				// for (int i = 0; i < 1; i++) {
				// loanMap = new HashMap<String, Object>();
				// loanMap.put("investor", "xxxx");
				// loanMap.put("investTime", "2015-12-15 17:08:20");
				// loanMap.put("investAmount", "180.00");
				// loanList.add(loanMap);
				// }

				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 还款计划
				Map<String, Object> objMap = new HashMap<String, Object>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < 11; i++) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("planDate", "2015-12-15 17:08:20");
					loanMap.put("interest", "5.00");
					loanMap.put("pricipal", "200.00");
					loanList.add(loanMap);
				}

				objMap.put("pageCount", "1");
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("5".equals(catalog)) {
				// 投资操作
				// /mobile/loanRequest.json?userId=229&loanId=424&investAmount=200&dealPwd=123456
				Integer userId = params.getInt("userId");
				Integer loanId = params.getInt("loanId");
				Double investMoney = params.getDouble("investAmount");
				String dealPwd = params.getString("dealPwd");

				if (userId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求用户id不能为空");
					return result;
				} else if (loanId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求项目id不能为空");
					return result;
				} else if (investMoney <= 0) {
					result.setResultCode("201");
					result.setResultMessage("请输入投资金额");
					return result;
				} else if (dealPwd == null || "".equals(dealPwd)) {
					result.setResultCode("201");
					result.setResultMessage("请输入交易密码");
					return result;
				}
				// 检查用户信息
				if (!MobileHelper.check(userService, userId, result)
						.isSuccess()) {
					return result;
				}

				// 投资
				User user = userService.findEntityById(userId).getData();
				if (user != null) { // 校验交易密码
					PlainResult<UserDO> userDoResult = userService
							.findById(user.getUserId());
					if (dealPwd == null
							|| !CryptUtils.md5(dealPwd).equals(
									userDoResult.getData().getUserDealPwd())) {
						result.setResultCode("201");
						result.setResultMessage("支付密码不正确!");
						return result;
					}
				}
				// 判断账户余额
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null
						|| user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();
				String accountMark = account.getAccountMark();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(
						accountMark, "1");
				if (investMoney - accountBacance[1] > 0) {
					result.setResultCode("201");
					result.setResultMessage("可用余额不足，请充值!");
					return result;
				}

				BidType bidType = BidType.COMMON_LOAN;
				// if (loanId != null && flagLoan == 1) { // 普通标
				// bidType = BidType.COMMON_LOAN;
				// }
				// if (loanId != null && flagLoan == 2) { // 转让标
				// bidType = BidType.TRANSFER_LOAN;
				// }

				// int userId = user.getUserId();
				// if (bidType == null) {
				// bidType = BidType.BUY_LOAN;
				// }

				// int bidId = params.getInt("loanId");
				Invest inv = new Invest();
				inv.setUserId(userId);
				inv.setBidType(bidType); // 设置标的类型

				// if (BidType.COMMON_LOAN.equals(bidType)) {
				inv.setBidId(loanId);
				inv.setOriginId(loanId);
				// } else {
				// inv.setBidId(loanId);
				// }

				// Double investMoney = params.getDouble("investedMoney");
				// if (investMoney != null) {
				inv.setInvestMoney(BigDecimal.valueOf(investMoney));
				// }
				List<Integer> redsendIdList = new ArrayList<Integer>();
				PlainResult<Integer> investCreateResult = investService
						.createInvest(inv, redsendIdList);
				if (!investCreateResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				// 支付
				// if (BidType.BUY_LOAN.equals(inv.getBidType())) {
				// return JsonBaseVO.SUCCESS;
				// } else {
				// PlainResult<Invest> investQueryResult =
				// investQueryService.queryById(investCreateResult.getData());
				// if (!investQueryResult.isSuccess()) {
				// result.setResultCode("201");
				// result.setResultMessage(investCreateResult.getMessage());
				// return result;
				// }

				// BaseResult invokeResult =
				// dealRecordService.invokePayment(investQueryResult.getData().getInnerSeqNo());
				// if (!invokeResult.isSuccess()) {
				// result.setResultCode("201");
				// result.setResultMessage(investCreateResult.getMessage());
				// return result;
				// }
				result.setResultCode("200");
				result.setResultMessage("投资成功");
				return result;
				// }
			}
			// 推荐投资
			else if ("6".equals(catalog)) {
				return queryOptimization(context, params);
			}
			// 借款人简介
			else if ("9".equals(catalog)) {
				return borrowerIntroduction(params);
			}
			// 相关文件
			else if ("10".equals(catalog)) {
				return releventIntroduction(params);
			}
			// 风控信息
			else if ("11".equals(catalog)) {
				return riskIntroduction(params);
			} else {
				result.setResultCode("201");
				result.setResultMessage("请求参数异常");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}

	/**
	 * 风控信息
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO riskIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("riskIntroduction", loan.getRiskIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		objMap.put("safes", fileUploadInfoService.findListByFileUrl(loanFileUrl,
				FileUploadSecondaryClass.SAFE_DATA.getType()));
		return vo;
	}

	/**
	 * 相关文件
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO releventIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("releventIntroduction", loan.getRelevantIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		// 实地资料
		objMap.put("spots", fileUploadInfoService.findListByFileUrl(loanFileUrl,
				FileUploadSecondaryClass.QUA_DATA.getType()));
		// 资质资料
		objMap.put("qualifys", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.SPOT_DATA.getType()));
		// 其他
		objMap.put("others", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.IMAGE_DATA.getType()));
		return vo;
	}

	/**
	 * 借款人简介
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO borrowerIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("borrowerIntroduction", loan.getBorrowerIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		//借款人资料
		objMap.put("borrowerInfos", fileUploadInfoService.findListByFileUrl(loanFileUrl,
				FileUploadSecondaryClass.GUA_DATA.getType()));
		return vo;
	}

	/**
	 * 推荐投资
	 * 
	 * @param context
	 * @param params
	 * @return
	 */
	private JsonMobileVO queryOptimization(Context context,
			ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		vo.setResultCode("200");
		vo.setResultMessage("successful");
		Map<String, Object> r = new HashMap<String, Object>();
		vo.setResult(r);

		List<Loan> loanList = this.loanQueryService.queryOptimization(1)
				.getData();
		Loan loan = loanList.size() > 0 ? loanList.get(0) : null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(1);
		r.put("list", list);
		if (loan != null) {
			Map<String, Object> loanMap = new HashMap<String, Object>();
			loanMap.put("loanId", loan.getLoanId());
			loanMap.put("loanType", loan.getLoanCategory().category);
			loanMap.put("loanIsnew", 1);
			loanMap.put("loanTitle", loan.getLoanNo());
			loanMap.put("loanMoney", loan.getLoanMoney());
			loanMap.put("loanRate", loan.getLoanRate());
			loanMap.put("loanPeriod", loan.getLoanPeriod()
					+ loan.getLoanPeriodUnit().getPrompt());//
			loanMap.put("loanProgress", Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney()).intValue());
			loanMap.put("investEndtime", DateUtil.formatDate(loan.getLoanInvestEndtime()));
			loanMap.put("loanExpireTime", DateUtil.formatDate(loan.getLoanExpireDate()));
			list.add(loanMap);
			r.put("pageCount ", 1);
			r.put("list", list);
		} else {
			r.put("pageCount ", 0);
		}
		return vo;

	}

}
