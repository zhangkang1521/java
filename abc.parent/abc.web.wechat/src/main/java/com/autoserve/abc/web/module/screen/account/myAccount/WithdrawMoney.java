package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class WithdrawMoney {
	private final static Logger logger = LoggerFactory
			.getLogger(WithdrawMoney.class); // 加入日志

	@Autowired
	private HttpSession session;
	@Autowired
	private AccountInfoService accountInfoService;
	@Autowired
	private DealRecordService dealrecordservice;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
	private ToCashService tocashservice;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserService userService;
	@Autowired
	private DeployConfigService deployConfigService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			nav.redirectToLocation(deployConfigService.getLoginUrl(request));
			return;
		}
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(user.getUserId());
		if (user.getUserType() == null || user.getUserType().getType() == 1) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		PlainResult<Account> result = accountInfoService
				.queryByUserId(userIdentity);
		Account acc = result.getData();

		Double money = params.getDouble("money");
		String mey = params.getString("money");

		if (money != null && !"".equals(money) && acc != null
				&& !"".equals(acc)) {
			String param = params.getString("param");
			// 生成订单号
			InnerSeqNo innerseqno = InnerSeqNo.getInstance();
			Map<String, Object> map = new HashMap<String, Object>();
			if (param.equals("1")) { // 快速提现
				String CardNo = params.getString("CardNo");
				map.put("WithdrawMoneymoremore", acc.getAccountMark());
				map.put("PlatformMoneymoremore", "");
				map.put("OrderNo", innerseqno.toString());
				map.put("Amount", mey);
				// map.put("FeePercent", "0");
				map.put("FeeMax", "");
				map.put("FeeRate", "");
				map.put("CardNo", CardNo);
				map.put("CardType", "");
				map.put("BankCode", "");
				map.put("BranchBankName", "");
				map.put("Province", "");
				map.put("City", "");
				map.put("Remark1", "");
				map.put("Remark2", "");
				map.put("Remark3", "");
				map.put("ReturnURL", "");
				map.put("NotifyURL", "");
				map.put("SignInfo", "");
			} else { // 申请提现
				String CardNo = params.getString("CardNo");
				String Cardtype = params.getString("CardType");
				String BankCode = params.getString("BankCode");
				String BranchBankName = params.getString("BranchBankName");
				String Province = params.getString("Province");
				String City = params.getString("City");
				/*
				 * 判断银行卡在数据库中是否存在 1.存在，更新 2.不存在，插入
				 */
				PlainResult<BankInfoDO> bankResult = bankinfoservice
						.queryBankInfo(user.getUserId().toString(), CardNo);
				BankInfo bankInfo = new BankInfo();
				if (bankResult.isSuccess()) {
					bankInfo.setBankId(bankResult.getData().getBankId());
					bankInfo.setBankUserId(user.getUserId());
					bankInfo.setBankLawer(user.getUserRealName());
					bankInfo.setBankUserType(user.getUserType().getType());
					if (Cardtype.equals("0")) {
						bankInfo.setCardType(CardType.DEBIT_CARD);
					}
					if (Cardtype.equals("1")) {
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
					if (Cardtype.equals("0")) {
						bankInfo.setCardType(CardType.DEBIT_CARD);
					}
					if (Cardtype.equals("1")) {
						bankInfo.setCardType(CardType.CREDIT_CARD);
					}
					bankInfo.setBankNo(CardNo);
					bankInfo.setBankName(BranchBankName);
					bankInfo.setBankCode(BankCode);
					bankInfo.setAreaCode(City);
					bankInfo.setCardStatus(CardStatus.STATE_DISABLE);
					bankInfo.setBindDate(new Date());
					bankinfoservice.createBankInfo(bankInfo);
					bankResult = bankinfoservice.queryBankInfo(user.getUserId()
							.toString(), CardNo);
				}
				map.put("WithdrawMoneymoremore", acc.getAccountMark());
				map.put("PlatformMoneymoremore", "");
				map.put("OrderNo", innerseqno.toString());
				map.put("Amount", mey);
				// map.put("FeePercent", "0");
				map.put("FeeMax", "");
				map.put("FeeRate", "");
				map.put("CardNo", CardNo);
				map.put("CardType", Cardtype);
				map.put("BankCode", BankCode);
				map.put("BranchBankName", BranchBankName);
				map.put("Province", Province);
				map.put("City", City);
				map.put("Remark1", bankResult.getData().getBankId().toString());
				map.put("Remark2", "");
				map.put("Remark3", "");
				map.put("ReturnURL", "");
				map.put("NotifyURL", "");
				map.put("SignInfo", "");
			}
			// 提现金额
			BigDecimal cashMoney = new BigDecimal(money);

			// 计算手续费比例,存入map
			BaseResult resultx = tocashservice.calculationPlatformFee(
					user.getUserId(), cashMoney, map);
			if (!resultx.isSuccess()) {
				context.put("Message", resultx.getMessage());
				nav.forwardTo("/error").end();
			}

			PlainResult<DealReturn> paramMap = tocashservice.toCashOther(
					user.getUserId(), user.getUserType(), cashMoney, map);
			JSONObject jo = JSON.parseObject(paramMap.getData().getParams());

			context.put("jo", jo);
			context.put("user", user);
		}
	}
}
