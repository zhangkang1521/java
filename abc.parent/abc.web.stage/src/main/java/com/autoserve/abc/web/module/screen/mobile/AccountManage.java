package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 帐号管理
 * 
 * @author Bo.Zhang
 */
public class AccountManage {

	@Resource
	private UserService userService;

	@Resource
	private AccountInfoService accountInfoService;

	@Resource
	private BankInfoService bankInfoService;

	@Resource
	private DoubleDryService doubleDryService;

	@Resource
	private CardCityBaseService cardCityBaseService;

	@Resource
	private RechargeService rechargeService;

	@Resource
	private ToCashService toCashService;

	@Resource
	private DealRecordService dealRecordService;
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
	private InvestService investservice;
	@Resource
	private RealnameAuthService realnameAuthService;
	@Resource
	private CompanyCustomerService companyCustomerService;
	@Autowired
	private DoubleDryService doubledryservice;
	@Resource
	private SysConfigService sysConfigService;
	@Autowired
	private PaymentPlanService paymentPlanService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");
			Integer userId = params.getInt("userId");

			if (userId == null || "".equals(userId)) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}

			if ("1".equals(catalog)) {
				// 账户金额
				UserDO userDO = userService.findById(userId).getData();
				if (userDO == null) {
					result.setResultCode("200");
					result.setResultMessage("不存在的用户");
					return result;
				}
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == 1) {
					userIdentity.setUserType(UserType.PERSONAL);
				} else {
					userIdentity.setUserType(UserType.ENTERPRISE);
				}
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				String accountMark = account.getData().getAccountMark(); // 多多号id
				Double[] accountBalance = { 0.00, 0.00, 0.00 };
				if (accountMark != null && !"".equals(accountMark)) {
					accountBalance = this.doubleDryService.queryBalance(
							accountMark, "1");
				}
				Map<String, Object> resultMap = Maps.newHashMap();
				result.setResult(resultMap);
				// 可用金额
				BigDecimal avaiMoney = new BigDecimal(
						Double.toString(accountBalance[1]));
				BigDecimal blockMoney = new BigDecimal(
						Double.toString(accountBalance[2]));
				resultMap.put("avaiMoney", avaiMoney);
				// 冻结金额
				resultMap.put("blockMoney", blockMoney);
				// 待收待还金额
				PlainResult<Map<String, BigDecimal>> planinResult = investservice
						.findTotalIncomeAndPayMoneyByUserId(userDO.getUserId());
				BigDecimal incomeMoney = planinResult.getData().get(
						"incomeMoney");
				BigDecimal payMoney = planinResult.getData().get("payMoney");
				resultMap.put("incomeMoney", incomeMoney);
				resultMap.put("payMoney", payMoney);
				// 资产总额
				BigDecimal allMoney = avaiMoney.add(blockMoney)
						.add(incomeMoney).subtract(payMoney);
				resultMap.put("allMoney", allMoney);
				resultMap.put("userRealName",
						MobileHelper.nullToEmpty(userDO.getUserRealName()));
				resultMap.put("userEmail",
						MobileHelper.nullToEmpty(userDO.getUserEmail()));
				return result;
			} else if ("2".equals(catalog)) {
				// 修改用户信息
				// /mobile/accountManage.json?catalog=2&userId=235&param={"userEmail":"asdf","userRealName":"张康2","userDocNo":"340821"}
				String param = params.getString("param");
				System.out.println(param);
				UserDO userDO = JSON.parseObject(param, UserDO.class);
				userDO.setUserId(userId);
				BaseResult baseResult = userService.modifyUserSelective(userDO);
				if (baseResult.isSuccess()) {
					result.setResultCode("200");
					result.setResultMessage("保存信息成功");
				} else {
					result.setResultCode("201");
					result.setResultMessage(baseResult.getMessage());
				}
			} else if ("3".equals(catalog)) {
				// 银行卡列表
				ListResult<BankInfoDO> listResult = bankInfoService
						.queryListBankInfo(userId);
				List<BankInfoDO> list = listResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (BankInfoDO bankInfoDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("bankId", bankInfoDO.getBankId());
					loanMap.put("bankNumber", bankInfoDO.getBankNo());
					loanMap.put("bankName", bankInfoDO.getBankName());
					loanMap.put("BankCode", bankInfoDO.getBankCode());
					loanMap.put("BankIcon", "");
					loanList.add(loanMap);
				}

				objMap.put("pageCount", 1);
				objMap.put("list", JSON.toJSON(loanList));
				objMap.put("isAddCard", "1");

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 添加银行卡
				String param = params.getString("param");
				String md5Flag = params.getString("md5Flag");

				if (param == null || "".equals(param)) {
					List<CardCityBaseDO> list = cardCityBaseService
							.queryAllCity().getData();
					String md5 = Md5Encrypt.md5(JSON.toJSONString(list));
					if (!md5.equals(md5Flag)) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						objMap.put("md5Flag", md5);
						objMap.put("cardCityList", JSON.toJSON(list));

						result.setResult(JSON.toJSON(objMap));
					}
					result.setResultCode("200");
				} else {
					User user = userService.findEntityById(userId).getData();

					JSONObject jObject = JSON.parseObject(param);
					BankInfo bankInfo = new BankInfo();
					bankInfo.setBankUserId(user.getUserId());
					bankInfo.setBankLawer(user.getUserRealName());
					bankInfo.setBankUserType(user.getUserType().getType());
					if (jObject.getInteger("cardType") == 0) {
						bankInfo.setCardType(CardType.DEBIT_CARD);
					} else if (jObject.getInteger("cardType") == 1) {
						bankInfo.setCardType(CardType.CREDIT_CARD);
					}
					bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
					bankInfo.setBankNo(jObject.getString("cardNo"));
					bankInfo.setBankName(jObject.getString("branchBankName"));
					bankInfo.setBankCode(jObject.getString("bankCode"));
					bankInfo.setAreaCode(jObject.getString("city"));
					BaseResult baseResult = bankInfoService
							.createBankInfo(bankInfo);
					if (baseResult.isSuccess()) {
						result.setResultCode("200");
						result.setResultMessage("添加银行卡成功");
					} else {
						result.setResultCode("201");
						result.setResultMessage(baseResult.getMessage());
					}
				}
			} else if ("5".equals(catalog)) {
				// 账户充值
				// /mobile/accountManage.json?catalog=5&userId=131&money=100
				Double money = params.getDouble("money");
				String mey = params.getString("money");

				// if (!MobileHelper.check(userService, userId, result)) {
				// return result;
				// }

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				if (user.getUserType() == null || "".equals(user.getUserType())
						|| user.getUserType() == UserType.PERSONAL) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				userIdentity.setUserId(user.getUserId());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					// Deal deal = new Deal();
					// InnerSeqNo innerseqno = InnerSeqNo.getInstance();
					// deal.setInnerSeqNo(innerseqno);
					// deal.setBusinessType(DealType.RECHARGE);
					// deal.setOperator(user.getUserId());
					//
					// List<DealDetail> list = new ArrayList<DealDetail>();
					// DealDetail dealdetail = new DealDetail();
					Map<String, String> param = new HashMap<String, String>();
					param.put("RechargeMoneymoremore", account.getAccountMark());
					param.put("PlatformMoneymoremore", "");
					// param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					// param.put("RechargeType", "2");
					// param.put("FeeType", "1");
					param.put("RechargeType", "");
					param.put("FeeType", "");
					param.put("CardNo", "");
					param.put("RandomTimeStamp", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");

					// dealdetail.setData(param);
					// BigDecimal bigdecimal = new BigDecimal(money);
					// dealdetail.setMoneyAmount(bigdecimal);
					// dealdetail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
					// list.add(dealdetail);
					//
					// deal.setDealDetail(list);

					// PlainResult<DealReturn> paramMap =
					// dealRecordService.createBusinessRecord(deal, null);
					PlainResult<DealReturn> paramMap = rechargeService
							.recharge(user.getUserId(), user.getUserType(),
									new BigDecimal(money), param);
					JSONObject jo = JSON.parseObject(paramMap.getData()
							.getCashRecords().get(0).getCrRequestParameter());
					String postData = "RechargeType="
							+ jo.get("RechargeType")
							+ "&RechargeMoneymoremore="
							+ jo.get("RechargeMoneymoremore")
							+ "&OrderNo="
							+ jo.get("OrderNo")
							+ "&Amount="
							+ jo.get("Amount")
							+ "&FeeType="
							+ jo.get("FeeType")
							+ "&CardNo="
							+ jo.get("CardNo")
							+ "&PlatformMoneymoremore="
							+ jo.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(jo
									.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(),
									"UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", jo.get("submitUrl"));
					objMap.put("postData", postData);
					result.setResultCode("200");
					result.setResult(JSON.toJSON(objMap));
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("充值失败");
				}
			} else if ("6".equals(catalog)) {
				// 账户提现
				// /mobile/accountManage.json?catalog=6&money=100&userId=156&bankId=36
				Double money = params.getDouble("money");
				String mey = params.getString("money");
				Integer bankId = params.getInt("bankId");

				// if (!MobileHelper.check(userService, userId, result)) {
				// return result;
				// }

				User user = userService.findEntityById(userId).getData();
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

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					// 生成订单号
					InnerSeqNo innerseqno = InnerSeqNo.getInstance();

					BankInfoDO bankInfoDO = bankInfoService
							.queryListBankInfoById(bankId).getData();

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("WithdrawMoneymoremore", account.getAccountMark());
					param.put("PlatformMoneymoremore", "");
					param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					param.put("FeePercent", "0");
					param.put("FeeMax", "");
					param.put("FeeRate", "");
					param.put("CardNo", bankInfoDO.getBankNo());
					param.put("CardType", bankInfoDO.getCardType() + "");
					param.put("BankCode", bankInfoDO.getBankCode());
					param.put("BranchBankName", bankInfoDO.getBankName());
					param.put(
							"Province",
							cardCityBaseService
									.queryCardByCode(
											Integer.valueOf(bankInfoDO
													.getAreaCode())).getData()
									.getProvCode().toString());
					// param.put("Province", "1");
					param.put("City", bankInfoDO.getAreaCode());
					param.put("Remark1", "");
					param.put("Remark2", "");
					param.put("Remark3", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");
					param.put("SignInfo", "");

					// 计算手续费比例,存入map
					BaseResult resultx = toCashService.calculationPlatformFee(
							user.getUserId(), new BigDecimal(mey), param);
					if (!resultx.isSuccess()) {
						result.setResultCode("500");
						result.setResultMessage(resultx.getMessage());
						return result;
					}

					PlainResult<DealReturn> paramMap = toCashService
							.toCashOther(user.getUserId(), user.getUserType(),
									new BigDecimal(money), param);
					System.out.println("发送参数:======"
							+ paramMap.getData().getCashRecords().get(0)
									.getCrRequestParameter());

					JSONObject jo = JSON.parseObject(paramMap.getData()
							.getCashRecords().get(0).getCrRequestParameter());
					String postData = "WithdrawMoneymoremore="
							+ jo.get("WithdrawMoneymoremore")
							+ "&OrderNo="
							+ jo.get("OrderNo")
							+ "&Amount="
							+ jo.get("Amount")
							+ "&FeePercent="
							+ jo.get("FeePercent")
							+ "&FeeMax="
							+ jo.get("FeeMax")
							+ "&FeeRate="
							+ jo.get("FeeRate")
							+ "&CardNo="
							+ URLEncoder.encode(jo.get("CardNo").toString(),
									"UTF-8")
							+ "&CardType="
							+ jo.get("CardType")
							+ "&BankCode="
							+ jo.get("BankCode")
							+ "&BranchBankName="
							+ jo.get("BranchBankName")
							+ "&Province="
							+ jo.get("Province")
							+ "&City="
							+ jo.get("City")
							+ "&Remark1="
							+ jo.get("Remark1")
							+ "&Remark2="
							+ jo.get("Remark2")
							+ "&Remark3="
							+ jo.get("Remark3")
							+ "&PlatformMoneymoremore="
							+ jo.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(jo
									.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(),
									"UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", jo.get("submitUrl"));
					objMap.put("postData", postData);
					result.setResultCode("200");
					result.setResult(JSON.toJSON(objMap));
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("提现失败");
				}
			} else if ("61".equals(catalog)) {
				// 账户提现(绑卡)
				// /mobile/accountManage.json?catalog=61&money=345&cardNo=6217788302900174767&cardType=0&branchBankName=zhihang001&Province=18&city=1183&userId=156&bankCode=1
				Double money = params.getDouble("money");
				String mey = params.getString("money");

				String CardNo = params.getString("cardNo");
				String CardType1 = params.getString("cardType");
				String BankCode = params.getString("bankCode");
				String BranchBankName = params.getString("branchBankName");
				String Province = params.getString("Province");
				String City = params.getString("city");
				User user = userService.findEntityById(userId).getData();
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

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					// 生成订单号
					InnerSeqNo innerseqno = InnerSeqNo.getInstance();
					// 添加银行卡(存在改银行卡则更新，否则添加)
					PlainResult<BankInfoDO> bankResult = bankinfoservice
							.queryBankInfo(user.getUserId().toString(), CardNo);
					BankInfo bankInfo = new BankInfo();
					if (bankResult.isSuccess()) {
						bankInfo.setBankId(bankResult.getData().getBankId());
						bankInfo.setBankUserId(user.getUserId());
						bankInfo.setBankLawer(user.getUserRealName());
						bankInfo.setBankUserType(user.getUserType().getType());
						if (CardType1.equals("0")) {
							bankInfo.setCardType(CardType.DEBIT_CARD);
						}
						if (CardType1.equals("1")) {
							bankInfo.setCardType(CardType.CREDIT_CARD);
						}
						bankInfo.setBankNo(CardNo);
						bankInfo.setBankName(BranchBankName);
						bankInfo.setBankCode(BankCode);
						bankInfo.setAreaCode(City);
						// bankInfo.setCardStatus(CardStatus.STATE_DISABLE);
						bankInfo.setBindDate(new Date());
						bankinfoservice.modifyBankInfo(bankInfo);
					} else {
						bankInfo.setBankUserId(user.getUserId());
						bankInfo.setBankLawer(user.getUserRealName());
						bankInfo.setBankUserType(user.getUserType().getType());
						if (CardType1.equals("0")) {
							bankInfo.setCardType(CardType.DEBIT_CARD);
						} else if (CardType1.equals("1")) {
							bankInfo.setCardType(CardType.CREDIT_CARD);
						}
						bankInfo.setCardStatus(CardStatus.STATE_DISABLE);
						bankInfo.setBankNo(CardNo);
						bankInfo.setBankName(BranchBankName);
						bankInfo.setBankCode(BankCode);
						bankInfo.setAreaCode(City);
						bankInfoService.createBankInfo(bankInfo);
					}

					BankInfoDO bankInfoDO1 = bankInfoService.queryBankInfo(
							userId.toString(), CardNo).getData();

					String BankId = bankInfoDO1.getBankId().toString();
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("WithdrawMoneymoremore", account.getAccountMark());
					param.put("PlatformMoneymoremore", "");
					param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					// param.put("FeePercent", "0");
					param.put("FeeMax", "");
					param.put("FeeRate", "");
					param.put("CardNo", CardNo);
					param.put("CardType", CardType1);
					param.put("BankCode", BankCode);
					param.put("BranchBankName", BranchBankName);
					// param.put("Province",
					// cardCityBaseService.queryCardByCode(Integer.valueOf(bankInfoDO.getAreaCode())).getData().getProvCode().toString());
					param.put("Province", Province);
					param.put("City", City);
					param.put("Remark1", BankId);
					param.put("Remark2", "");
					param.put("Remark3", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");
					param.put("SignInfo", "");

					// 计算手续费比例,存入map
					BaseResult resultx = toCashService.calculationPlatformFee(
							user.getUserId(), new BigDecimal(mey), param);
					if (!resultx.isSuccess()) {
						result.setResultCode("500");
						result.setResultMessage(resultx.getMessage());
						return result;
					}

					PlainResult<DealReturn> paramMap = toCashService
							.toCashOther(user.getUserId(), user.getUserType(),
									new BigDecimal(money), param);
					System.out.println("发送参数:======"
							+ paramMap.getData().getCashRecords().get(0)
									.getCrRequestParameter());

					JSONObject jo = JSON.parseObject(paramMap.getData()
							.getCashRecords().get(0).getCrRequestParameter());
					String postData = "WithdrawMoneymoremore="
							+ jo.get("WithdrawMoneymoremore")
							+ "&OrderNo="
							+ jo.get("OrderNo")
							+ "&Amount="
							+ jo.get("Amount")
							+ "&FeePercent="
							+ jo.get("FeePercent")
							+ "&FeeMax="
							+ jo.get("FeeMax")
							+ "&FeeRate="
							+ jo.get("FeeRate")
							+ "&CardNo="
							+ URLEncoder.encode(jo.get("CardNo").toString(),
									"UTF-8")
							+ "&CardType="
							+ jo.get("CardType")
							+ "&BankCode="
							+ jo.get("BankCode")
							+ "&BranchBankName="
							+ jo.get("BranchBankName")
							+ "&Province="
							+ jo.get("Province")
							+ "&City="
							+ jo.get("City")
							+ "&Remark1="
							+ jo.get("Remark1")
							+ "&Remark2="
							+ jo.get("Remark2")
							+ "&Remark3="
							+ jo.get("Remark3")
							+ "&PlatformMoneymoremore="
							+ jo.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(jo
									.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(),
									"UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", jo.get("submitUrl"));
					objMap.put("postData", postData);
					result.setResultCode("200");
					result.setResult(JSON.toJSON(objMap));
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("提现失败");
				}
			} else if ("7".equals(catalog)) {
				// 交易记录
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");

				UserDO userDO = userService.findById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				if (userDO.getUserType() == null
						|| "".equals(userDO.getUserType())
						|| userDO.getUserType() == UserType.PERSONAL.getType()) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity
						.setUserType(UserType.valueOf(userDO.getUserType()));
				userIdentity.setUserId(userDO.getUserId());
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				Map<String, Object> objMap = new HashMap<String, Object>();
				if (account.getData().getAccountMark() == null) {
					objMap.put("pageCount", 0);
					objMap.put("list", new ArrayList(0));
					result.setResult(objMap);
					return result;
				}

				DealRecordDO dealrecorddo = new DealRecordDO();
				dealrecorddo
						.setDrPayAccount(account.getData().getAccountMark());
				PageResult<DealRecordDO> pageResult = dealRecordService
						.queryDealByParams(dealrecorddo, new PageCondition(
								showPage, pageSize), null, null);
				List<DealRecordDO> list = pageResult.getData();

				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (DealRecordDO dealRecordDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("tradeType",
							DealType.valueOf(dealRecordDO.getDrType()).getDes()); // 交易类型
					loanMap.put("traderStatus",
							DealState.valueOf(dealRecordDO.getDrState())
									.getDes()); // 交易状态
					loanMap.put("tradeTime", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(dealRecordDO
							.getDrOperateDate())); // 交易时间
					loanMap.put("transMoney", dealRecordDO.getDrMoneyAmount()); // 交易金额
					loanMap.put("transCharges", "0.00"); // 交易手续费
					loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo()
							.substring(0, 15) + "..."); // 交易流水号
					// loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo());
					// //交易流水号
					loanMap.put("receiveAccount",
							dealRecordDO.getDrReceiveAccount()); // 交易对方
					loanList.add(loanMap);
				}

				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("8".equals(catalog)) {
				// 修改密码
				String oldPwd = params.getString("oldPwd");
				String newPwd = params.getString("newPwd");

				PlainResult<UserDO> plainResult = userService.findById(userId);
				UserDO userDO = plainResult.getData();
				if (!userDO.getUserPwd().equals(CryptUtils.md5(oldPwd))) {
					result.setResultCode("201");
					result.setResultMessage("输入的旧密码不正确");
				} else {
					userDO.setUserPwd(CryptUtils.md5(newPwd));
					BaseResult baseResult = userService
							.modifyUserSelective(userDO);
					if (baseResult.isSuccess()) {
						result.setResultCode("200");
						result.setResultMessage("修改密码成功");
					} else {
						result.setResultCode("201");
						result.setResultMessage("修改密码失败");
					}
				}
			} else if ("9".equals(catalog)) {
				// 账户余额
				UserDO userDO = userService.findById(userId).getData();

				Map<String, Object> objMap = new MobileMap<String, Object>();

				// 获取用户绑定的银行卡信息
				List<BankInfoDO> list = bankInfoService.queryListBankInfo(
						userId).getData();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				for (BankInfoDO bankInfoDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("bankId", bankInfoDO.getBankId());
					loanMap.put("bankNumber", bankInfoDO.getBankNo());
					loanMap.put("bankName", bankInfoDO.getBankName());
					loanMap.put("bankCode", bankInfoDO.getBankCode());
					loanMap.put("bankIcon", "");
					loanList.add(loanMap);
				}
				if (userDO.getUserEmail() != null) {
					objMap.put("userEmail",
							userDO.getUserEmail());
				}
				objMap.put("userRealName",
						userDO.getUserRealName());
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == null || userDO.getUserType() == 1) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity
						.setUserType(UserType.valueOf(userDO.getUserType()));
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();
				String accountMark = account.getAccountMark();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(
						accountMark, "1");

				objMap.put("allMoney", accountBacance[0]);
				objMap.put("avlMoney", accountBacance[1]);
				objMap.put("fraMoney", accountBacance[2]);
				objMap.put("bankList", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("10".equals(catalog)) {
				// 用户信息
				UserDO userDO = userService.findById(userId).getData();
				Map<String, Object> objMap = new MobileMap<String, Object>();
				if (userDO.getUserBusinessState() != null
						&& userDO.getUserBusinessState() > 1) {
					objMap.put("isOpenAccount", "1");
				} else {
					objMap.put("isOpenAccount", "0");
				}
				objMap.put("realName", userDO.getUserRealName());
				objMap.put("userName", userDO.getUserName());
				objMap.put("email", userDO.getUserName());
				objMap.put("userDocNo", userDO.getUserDocNo());
				objMap.put("userPhone", userDO.getUserPhone());
				result.setResult(objMap);
			}
			// 资金流水
			else if ("20".equals(catalog)) {
				result = cashSerial(params);
			}
			// 资金流水详细
			else if ("21".equals(catalog)) {
				result = orderDetail(params);
			}
			// 银行卡解绑
			else if ("22".equals(catalog)) {
				result = unBindBankCard(params);
			}
			// 开户
			else if ("23".equals(catalog)) {
				result = openAccount(params);
			}
			// 授权
			else if ("24".equals(catalog)) {
				result = auth(params);
			}
			// 帐号状态检查
			else if ("25".equals(catalog)) {
				result = check(params);
			}
			// 提现前显示
			else if ("26".equals(catalog)) {
				result = toCashBefore(params);
			}
			// 修改交易密码
			else if ("27".equals(catalog)) {
				result = modifyDealPsw(params);
			} else {
				result.setResultCode("201");
				result.setResultMessage("catalog notFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("201");
			result.setResultMessage("error");
		}

		return result;
	}

	/**
	 * 修改交易密码
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO modifyDealPsw(ParameterParser params) {
		// /mobile/accountManage.json?catalog=27&userId=219&oldDealPsw=123456&newDealPsw=111111
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		String oldDealPsw = params.getString("oldDealPsw");
		int userId = params.getInt("userId");
		String newDealPsw = params.getString("newDealPsw");
		UserDO userDO = userService.findById(userId).getData();
		// 检验原交易密码是否相同
		if (!userDO.getUserDealPwd().equals(CryptUtils.md5(oldDealPsw))) {
			vo.setResultCode("201");
			vo.setResultMessage("原交易密码错误！");
			return vo;
		}
		// 修改交易密码
		UserDO userDO2 = new UserDO();
		userDO2.setUserId(userId);
		userDO2.setUserDealPwd(CryptUtils.md5(newDealPsw));
		BaseResult result = this.userService.modifyUserSelective(userDO2);
		if (!result.isSuccess()) {
			vo.setResultCode("500");
			vo.setMessage(result.getMessage());
			return vo;
		} else {
			vo.setMessage("修改成功！");
		}
		return vo;
	}

	/**
	 * 提现前信息展示
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO toCashBefore(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		PlainResult<User> result = userService.findEntityById(userId);
		// 免费提现额度
		BigDecimal cashQuota = result.getData().getUserCashQuota();
		if (cashQuota == null) {
			cashQuota = new BigDecimal(0);
		}
		// 本月的免费提现机会剩余次数
		int monthtimes = 0;
		PlainResult<SysConfig> monthfreeTocashTimesResult = sysConfigService
				.querySysConfig(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
		if (!monthfreeTocashTimesResult.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage("免费提现次数查询失败");
			return vo;
		}

		// 查询用户本月的提现次数
		PlainResult<Integer> resultx = toCashService
				.countTocashCurrentMonth(userId);
		if (!resultx.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage(resultx.getMessage());
			return vo;
		}
		// 用户的待还本金
		PlainResult<BigDecimal> waitPayCapital = paymentPlanService
				.queryWaitPayCapital(userId);
		PlainResult<SysConfig> payCapitalResult = sysConfigService
				.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
		if (!payCapitalResult.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage("待还本金的上限查询失败");
			return vo;
		}
		// 用户本月提现次数小于系统设置 && 用户待还本金没有超过系统设置
		if (resultx.getData() < Integer.parseInt(monthfreeTocashTimesResult
				.getData().getConfValue())
				&& waitPayCapital.getData().compareTo(
						new BigDecimal(payCapitalResult.getData()
								.getConfValue())) < 0) {
			monthtimes = Integer.parseInt(monthfreeTocashTimesResult.getData()
					.getConfValue()) - resultx.getData();
		}
		resultMap.put("cashQuota", cashQuota);
		resultMap.put("monthTimes", monthtimes);
		return vo;
	}

	/**
	 * 检查有无开户，授权
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO check(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");

		// 获取用户信息
		UserDO userDO = userService.findById(userId).getData();
		if (userDO == null) {
			vo.setResultCode("404");
			vo.setResultMessage("不存在的用户");
			return vo;
		}
		if (userDO.getUserBusinessState() == null
				|| userDO.getUserBusinessState() < 2) {
			resultMap.put("isOpenAccount", "0");
		} else {
			resultMap.put("isOpenAccount", "1");
		}
		if (userDO.getUserAuthorizeFlag() == null
				|| userDO.getUserAuthorizeFlag() == 0) {
			resultMap.put("isAuth", "0");
		} else {
			resultMap.put("isAuth", "1");
		}
		return vo;
	}

	/**
	 * 授权
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private JsonMobileVO auth(ParameterParser params)
			throws UnsupportedEncodingException {
		// /mobile/accountManage.json?catalog=24&userId=227
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");

		// 获取用户信息
		PlainResult<User> result = userService.findEntityById(userId);
		User user = result.getData();
		if (user == null) {
			vo.setResultCode("404");
			vo.setResultMessage("不存在的用户");
			return vo;
		}

		// 获取开户信息
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(user.getUserId());
		if (user.getUserType() == null || user.getUserType().getType() == 1) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);
		if (!account.isSuccess()) {
			vo.setResultCode("405");
			vo.setResultMessage("你还没有开户，请先开户");
			return vo;
		}

		// 授权参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("MoneymoremoreId", account.getData().getAccountMark());
		map.put("Remark1", account.getData().getAccountUserId().toString());
		map.put("AuthorizeTypeOpen", "1,2,3");
		Map<String, String> paramsMap = doubleDryService.authorize(map);

		// 手机端参数拼接
		StringBuffer sb = new StringBuffer();
		sb.append("MoneymoremoreId=" + paramsMap.get("MoneymoremoreId"));
		sb.append("&PlatformMoneymoremore="
				+ paramsMap.get("PlatformMoneymoremore"));
		sb.append("&AuthorizeTypeOpen=" + paramsMap.get("AuthorizeTypeOpen"));
		sb.append("&Remark1=" + paramsMap.get("Remark1"));
		sb.append("&ReturnURL=" + paramsMap.get("ReturnURL"));
		sb.append("&NotifyURL=" + paramsMap.get("NotifyURL"));
		sb.append("&SignInfo="
				+ URLEncoder.encode(paramsMap.get("SignInfo"), "UTF-8"));

		resultMap.put("submitUrl", paramsMap.get("SubmitURL"));
		resultMap.put("postData", sb.toString());
		return vo;
	}

	/**
	 * 开户
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private JsonMobileVO openAccount(ParameterParser params)
			throws UnsupportedEncodingException {
		// /mobile/accountManage.json?catalog=23&userId=233
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		// 实名认证
		BaseResult realNameResult = realnameAuthService.realNameAudit(userId);
		if (!realNameResult.isSuccess()) {
			vo.setResultCode("404");
			vo.setResultMessage(realNameResult.getMessage());
			return vo;
		}
		// 开户
		PlainResult<User> result = userService.findEntityById(userId);
		Map<String, String> param = new HashMap<String, String>();
		param.put("RegisterType", "2");
		if (result.getData().getUserType() == null
				|| "".equals(result.getData().getUserType())
				|| result.getData().getUserType() == UserType.PERSONAL) {
			param.put("AccountType", "");
			param.put("IdentificationNo", result.getData().getUserDocNo());
			param.put("RealName", result.getData().getUserRealName());
			param.put("Mobile", result.getData().getUserPhone());
			param.put("Email", result.getData().getUserEmail());
		} else {
			CompanyCustomerDO company = companyCustomerService.findByUserId(
					userId).getData();
			param.put("AccountType", "1");
			param.put("IdentificationNo", company.getCcLicenseNo());
			param.put("RealName", company.getCcCompanyName());
			param.put("Mobile", company.getCcContactPhone());
			param.put("Email", company.getCcContactEmail());
		}
		param.put("LoanPlatformAccount", result.getData().getUserName());
		param.put("Remark1", result.getData().getUserId().toString());
		PlainResult<Map> paramMap = doubledryservice.openAccent(param);
		System.out.println("发送参数:======" + paramMap.getData());
		Map data = paramMap.getData();
		StringBuffer sb = new StringBuffer();
		sb.append("RegisterType=" + data.get("RegisterType"));
		sb.append("&AccountType=" + data.get("AccountType"));
		sb.append("&Mobile=" + data.get("Mobile"));
		sb.append("&Email=" + data.get("Email"));
		sb.append("&RealName=" + data.get("RealName"));
		sb.append("&IdentificationNo=" + data.get("IdentificationNo"));
		sb.append("&Image1=" + MobileHelper.nullToEmpty(data.get("Image1")));
		sb.append("&Image2=" + MobileHelper.nullToEmpty(data.get("Image2")));
		sb.append("&LoanPlatformAccount=" + data.get("LoanPlatformAccount"));
		sb.append("&PlatformMoneymoremore=" + data.get("PlatformMoneymoremore"));
		sb.append("&RandomTimeStamp="
				+ MobileHelper.nullToEmpty(data.get("RandomTimeStamp")));
		sb.append("&Remark1=" + data.get("Remark1"));
		sb.append("&Remark2=" + MobileHelper.nullToEmpty(data.get("Remark2")));
		sb.append("&Remark3=" + MobileHelper.nullToEmpty(data.get("Remark3")));
		sb.append("&ReturnURL=" + data.get("ReturnURL"));
		sb.append("&NotifyURL=" + data.get("NotifyURL"));
		sb.append("&SignInfo="
				+ URLEncoder.encode(data.get("SignInfo").toString(), "UTF-8"));
		resultMap.put("submitUrl", paramMap.getData().get("SubmitURL"));
		resultMap.put("postData", sb.toString());
		return vo;

	}

	/**
	 * 银行卡解绑
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO unBindBankCard(ParameterParser params) {
		// /mobile/accountManage.json?catalog=22&userId=224&bankId=114
		JsonMobileVO vo = new JsonMobileVO();
		int userId = params.getInt("userId");
		String bankid = params.getString("bankId");
		BaseResult cardresult = new BaseResult();
		cardresult.setSuccess(false);

		/* 验证删除的卡号是否为该用户的 */
		Boolean checkresult = false;
		if (bankid != null && !"".equals(bankid)) {
			PlainResult<BankInfoDO> checkUserResult = bankinfoservice
					.queryListBankInfoById(Integer.valueOf(bankid));
			if (checkUserResult.isSuccess()
					&& checkUserResult.getData() != null) {
				Integer carduserid = checkUserResult.getData().getBankUserId();
				if (carduserid != null) {
					if (carduserid.intValue() == userId)
						checkresult = true;
				}
			}
		}

		if (checkresult) {
			BankInfo bankinfo = new BankInfo();
			bankinfo.setBankId(Integer.valueOf(bankid));
			bankinfo.setCardStatus(CardStatus.STATE_DISABLE);
			cardresult = bankinfoservice.modifyBankInfo(bankinfo);
		} else {
			vo.setResultCode("201");
			vo.setResultMessage("解绑失败");
		}
		return vo;
	}

	/**
	 * 资金流水详细
	 * 
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	private JsonMobileVO orderDetail(ParameterParser params)
			throws ParseException {
		JsonMobileVO mobileVo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		mobileVo.setResult(objMap);
		String orderNo = (String) params.get("orderNo");
		String cashType = (String) params.get("cashType");

		// 双乾接口参数
		Map<String, String> map = new HashMap<String, String>();
		if (orderNo != null && !"".equals(orderNo)) {
			map.put("OrderNo", orderNo);
		}
		if ("SZMX".equalsIgnoreCase(cashType)) {
			map.put("Action", "");
		} else if ("CZJL".equalsIgnoreCase(cashType)) {
			map.put("Action", "1");
		} else if ("CZJL".equalsIgnoreCase(cashType)) {
			map.put("Action", "2");
		}
		@SuppressWarnings("rawtypes")
		PlainResult<Map> result = doubleDryService.balanceAccount(map);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (result.getData() != null && "SZMX".equals(cashType)) {
			String TransferTime = sdf2.format(sdf.parse((String) result
					.getData().get("TransferTime")));
			String ActTime = sdf2.format(sdf.parse((String) result.getData()
					.get("ActTime")));

			String SecondaryTime = "";
			if (result.getData().get("SecondaryTime") != null
					&& !result.getData().get("SecondaryTime").equals("")) {
				SecondaryTime = sdf2.format(sdf.parse((String) result.getData()
						.get("SecondaryTime")));
			}
			objMap.put("TransferTime", TransferTime);
			objMap.put("ActTime", ActTime);
			objMap.put("SecondaryTime", SecondaryTime);
		} else if (result.getData() != null && "CZJL".equals(cashType)) {
			Date rechargetime = sdf.parse((String) result.getData().get(
					"RechargeTime"));
			String RechargeTime = sdf2.format(rechargetime);
			objMap.put("RechargeTime", RechargeTime);
		} else if (result.getData() != null && "TXJL".equals(cashType)) {
			String WithdrawsTime = "";
			String PlatformAuditTime = "";
			String WithdrawsBackTime = "";
			WithdrawsTime = (String) result.getData().get("WithdrawsTime");
			PlatformAuditTime = (String) result.getData().get(
					"PlatformAuditTime");
			WithdrawsBackTime = (String) result.getData().get(
					"WithdrawsBackTime");
			if (WithdrawsTime != null && !"".equals(WithdrawsTime)) {
				WithdrawsTime = sdf2.format(sdf.parse(WithdrawsTime));
			}
			if (PlatformAuditTime != null && !"".equals(PlatformAuditTime)) {
				PlatformAuditTime = sdf2.format(sdf.parse(PlatformAuditTime));
			}
			if (WithdrawsBackTime != null && !"".equals(WithdrawsBackTime)) {
				WithdrawsBackTime = sdf2.format(sdf.parse(WithdrawsBackTime));
			}
			objMap.put("WithdrawsTime", WithdrawsTime);
			objMap.put("PlatformAuditTime", PlatformAuditTime);
			objMap.put("WithdrawsBackTime", WithdrawsBackTime);
		}
		objMap.put("data", result.getData());
		return mobileVo;
	}

	/**
	 * 资金流水 /mobile/accountManage.json?catalog=20&userId=156&cashType=czjl
	 * 
	 * @param context
	 * @param params
	 */
	private JsonMobileVO cashSerial(ParameterParser params) {
		JsonMobileVO mobileVo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		mobileVo.setResult(objMap);
		String cashType = params.getString("cashType");// 必传字段
		Integer userId = params.getInt("userId");// 必传字段
		UserIdentity userIdentity = new UserIdentity();
		PlainResult<UserDO> uResult = userService.findById(userId);
		if (uResult.isSuccess()) {
			int userType = uResult.getData().getUserType();
			userIdentity.setUserType(UserType.valueOf(userType));
		}
		userIdentity.setUserId(userId);

		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);

		int currentPage = params.getInt("showPage");
		int pageSize = params.getInt("pageSize");

		PageCondition pageCondition = new PageCondition(currentPage, pageSize);

		String accountNo = account.getData().getAccountMark();
		if (StringUtils.isEmpty(accountNo)) {
			objMap.put("pageCount", 0);
			objMap.put("list", new ArrayList(0));
			return mobileVo;
		}
		// 收支明细
		if (cashType.equalsIgnoreCase("SZMX")) {
			DealRecordDO dealrecorddo = new DealRecordDO();
			dealrecorddo.setDrPayAccount(accountNo);
			PageResult<DealRecordDO> result = dealRecordService
					.queryDealByParams(dealrecorddo, pageCondition, null, null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", szmx(result.getData(), accountNo));
		}
		// 充值记录
		else if (cashType.equalsIgnoreCase("CZJL")) {
			RechargeRecordDO rechargerecorddo = new RechargeRecordDO();
			rechargerecorddo.setRechargeUserId(userId);
			rechargerecorddo.setRechargeState(1); // 成功
			PageResult<RechargeRecordDO> result = rechargeService
					.queryRechargeRecordByparam(rechargerecorddo,
							pageCondition, null, null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", czjl(result.getData()));
		}
		// 提现记录
		else if (cashType.equalsIgnoreCase("TXJL")) {
			TocashRecordDO tocashrecorddo = new TocashRecordDO();
			tocashrecorddo.setTocashUserId(userId);
			tocashrecorddo.setTocashState(1); // 提现成功
			PageResult<TocashRecordDO> result = toCashService
					.queryListByUserId(tocashrecorddo, pageCondition, null,
							null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", txjl(result.getData()));
		} else {
			mobileVo.setSuccess(false);
			mobileVo.setMessage("没有找到cashType");
		}
		return mobileVo;
	}

	/**
	 * 提现记录
	 * 
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> txjl(List<TocashRecordDO> list) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (TocashRecordDO tocashrecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("toCashDate",
					DateUtil.formatDate(tocashrecord.getTocashDate()));
			tempVo.put("toCashSeqNo", tocashrecord.getTocashSeqNo());
			tempVo.put("money", tocashrecord.getTocashAmount().toString());
			tempVo.put("type", "提现");
			switch (tocashrecord.getTocashState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}

	/**
	 * 充值记录
	 * 
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> czjl(List<RechargeRecordDO> list) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (RechargeRecordDO rechargerecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("rechargeDate",
					DateUtil.formatDate(rechargerecord.getRechargeDate()));
			tempVo.put("orderNo", rechargerecord.getRechargeSeqNo());
			tempVo.put("money", rechargerecord.getRechargeAmount().toString());
			tempVo.put("type", "充值");
			switch (rechargerecord.getRechargeState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}

	/**
	 * 收支明细
	 * 
	 * @param list
	 * @param accountNo
	 * @return
	 */
	private List<Map<String, Object>> szmx(List<DealRecordDO> list,
			String accountNo) {
		for (DealRecordDO dealRecordDO : list) {
			if (dealRecordDO.getDrDetailType() == 11) { // 平台手续费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
						+ "333");
			} else if (dealRecordDO.getDrDetailType() == 12) { // 担保服务费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
						+ "111");
			} else if (dealRecordDO.getDrDetailType() == 14) { // 转让手续费
				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
						+ "222");
			}
		}
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (DealRecordDO dealrecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("operateDate",
					DateUtil.formatDate(dealrecord.getDrOperateDate()));
			// 交易订单号
			if ((dealrecord.getDrType() == 2
					&& !dealrecord.getDrReceiveAccount().startsWith("p") && dealrecord
					.getDrDetailType() != 12) || (dealrecord.getDrType() == 3)) {
				tempVo.put("orderNo", dealrecord.getDrOutSeqNo());
			} else {
				tempVo.put("orderNo", dealrecord.getDrInnerSeqNo());
			}
			// 交易金额
			switch (dealrecord.getDrType()) {
			case 2:
			case 3:
				if (dealrecord.getDrPayAccount().equals(accountNo)) {
					tempVo.put("money", "-"
							+ dealrecord.getDrMoneyAmount().toString());
				} else if (dealrecord.getDrReceiveAccount().equals(accountNo)) {
					tempVo.put("money", dealrecord.getDrMoneyAmount()
							.toString());
				}
				break;
			case 6:
				tempVo.put("money", dealrecord.getDrMoneyAmount().toString());
				break;
			case 7:
				tempVo.put("money", "-"
						+ dealrecord.getDrMoneyAmount().toString());
				break;
			case 8:
				tempVo.put("money", dealrecord.getDrMoneyAmount().toString());
				break;
			default:
				tempVo.put("money", "-");
			}
			// 交易对方
			if ((dealrecord.getDrType() == 2 && accountNo.equals(dealrecord
					.getDrReceiveAccount()))
					|| (dealrecord.getDrType() == 3 && accountNo
							.equals(dealrecord.getDrReceiveAccount()))) {
				tempVo.put("receiveAccount", dealrecord.getDrPayAccount());
			} else {
				tempVo.put("receiveAccount", dealrecord.getDrReceiveAccount());
			}
			// 交易类型
			switch (dealrecord.getDrType()) {
			case 0:
				tempVo.put("type", "投资");
				break;
			case 1:
				tempVo.put("type", "撤投");
				break;
			case 2:
				if (dealrecord.getDrPayAccount().equals(accountNo)
						&& !dealrecord.getDrReceiveAccount().startsWith("p")
						&& dealrecord.getDrDetailType() != 12) {
					tempVo.put("type", "投资");
				} else if (dealrecord.getDrPayAccount().equals(accountNo)
						&& !dealrecord.getDrReceiveAccount().startsWith("p")
						&& dealrecord.getDrDetailType() == 12) {
					tempVo.put("type", "担保服务费");
				} else if (dealrecord.getDrPayAccount().equals(accountNo)
						&& dealrecord.getDrReceiveAccount().startsWith("p")) {
					tempVo.put("type", "手续费");
				} else if (dealrecord.getDrReceiveAccount().equals(accountNo)) {
					if(dealrecord.getDrDetailType()==DealDetailType.DEBT_TRANSFER_MONEY.type){
						tempVo.put("type", "转让");
					}else{
						tempVo.put("type", "借款");
					}
				}
				break;
			case 3:
				if (dealrecord.getDrPayAccount().equals(accountNo)) {
					tempVo.put("type", "还款");
				} else if (dealrecord.getDrReceiveAccount().equals(accountNo)) {
					tempVo.put("type", "收款");
				}
				break;
			case 4:
				tempVo.put("type", "充值");
				break;
			case 5:
				tempVo.put("type", "提现");
				break;
			case 6:
				tempVo.put("type", "退款");
				break;
			case 7:
				tempVo.put("type", "收购");
				break;
			case 8:
				tempVo.put("type", "流标");
				break;
			default:
				tempVo.put("type", "-");
			}
			// 状态
			switch (dealrecord.getDrState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}
}
