package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class Recharge {
    private final static Logger logger = LoggerFactory.getLogger(Recharge.class); //加入日志

    @Autowired
    private HttpSession         session;
    @Autowired
    private UserService         userservice;
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
    private DoubleDryService    doubleDryService;
    @Resource
    private HuifuPayService     huiFuPayServcice;
    @Resource
    private UserService           userService;

    public void execute(Context context, ParameterParser params) {

        logger.info("into Recharge execute");

        User user = (User) session.getAttribute("user");
        // User user=(User)session.getAttribute("user");
        PlainResult<User> result = userservice.findEntityById(user.getUserId());
        session.setAttribute("user", result.getData());
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> accountResult = accountInfoService.queryByUserId(userIdentity);
//        String accountMark = account.getData().getAccountNo(); //多多号id
//        Double[] accountBacance = { 0.00, 0.00, 0.00 };
//        if (accountMark != null && !"".equals(accountMark)) {
//            //调用汇付查询接口
//            Map<String, String> resultMap = new HashMap<String, String>();
//            resultMap.put("Version", SystemGetPropeties.getStrString("Version"));
//            resultMap.put("CmdId", "QueryBalanceBg");
//            resultMap.put("MerCustId", SystemGetPropeties.getStrString("MerCustId"));
//            resultMap.put("UsrCustId", accountMark);
//            Map<String, String> resultMapBalance = new HashMap<String, String>();
//            resultMapBalance = huiFuPayServcice.queryBalanceBg(SystemGetPropeties.getStrString("url"), resultMap);
//            accountBacance[0] = Double.valueOf(resultMapBalance.get("AcctBal"));
//            accountBacance[1] = Double.valueOf(resultMapBalance.get("AvlBal"));
//            accountBacance[2] = Double.valueOf(resultMapBalance.get("FrzBal"));
//        }
        String accountMark = accountResult.getData().getAccountMark();
		Double[] accountBacance = { 0.00, 0.00, 0.00 };
		if (StringUtils.isNotEmpty(accountMark)) {
			accountBacance = this.doubleDryService.queryBalance(accountMark,
					"1");
		}
        logger.info("余额为：" + accountBacance[1]);

        context.put("accountBacance", accountBacance);
        PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
        context.put("user", userDO.getData());
    }
}
