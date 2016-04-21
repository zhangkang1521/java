package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class RechargeMoney {
	private final static Logger logger = LoggerFactory
			.getLogger(RechargeMoney.class); // 鍔犲叆鏃ュ織

	@Autowired
	private UserService userservice;
	@Autowired
	private HttpSession session;
	@Autowired
	private DealRecordService dealrecordservice;
	@Autowired
	private AccountInfoService accountinfoservice;
	@Autowired
	private RechargeService rechargeservice;
	
	public void execute(Context context, ParameterParser params) {
		User user = (User) session.getAttribute("user");
		user = userservice.findEntityById(user.getUserId()).getData();

		UserIdentity userIdentity = new UserIdentity();
		if (user.getUserType() == null || "".equals(user.getUserType())
				|| user.getUserType() == UserType.PERSONAL) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		userIdentity.setUserId(user.getUserId());
		PlainResult<Account> account = accountinfoservice
				.queryByUserId(userIdentity);

		Double money = params.getDouble("money");
		String mey = params.getString("money");
		// 充值类型
		String rechargeType = params.getString("RechargeType");
		// 手续费类型
		String feeType = "";
		if (rechargeType == null) {
			rechargeType = "";
		} else {
			// 充值成功时从充值人账户全额扣除
			feeType = "1";
		}

		if (money != null && !"".equals(money) && account != null
				&& !"".equals(account)) {
			// 获取开户号
			String userOpenNo = account.getData().getAccountMark();
			if (userOpenNo == null || "".equals(userOpenNo)) {// 尚未开户

			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("RechargeMoneymoremore", userOpenNo);
			map.put("PlatformMoneymoremore", "");
			map.put("Amount", mey);
			map.put("RechargeType", rechargeType);
			map.put("FeeType", feeType);
			map.put("CardNo", "");
			map.put("RandomTimeStamp", "");
			map.put("ReturnURL", "");
			map.put("NotifyURL", "");
			BigDecimal bigdecimal = new BigDecimal(money);
			PlainResult<DealReturn> paramMap = rechargeservice.recharge(
					user.getUserId(), user.getUserType(), bigdecimal, map);
			JSONObject jo = JSON.parseObject(paramMap.getData().getParams());

			context.put("jo", jo);
		}

	}
}
