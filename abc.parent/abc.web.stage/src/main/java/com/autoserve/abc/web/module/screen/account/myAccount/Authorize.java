package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class Authorize {
	@Autowired
    private HttpSession session;
	@Autowired
    private AccountInfoService accountInfoService;
	@Resource
    private DoubleDryService doubleDryService;

    public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());	
    	 PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
    	 
    	 Map<String, String> map = new HashMap<String, String>();
    	 map.put("MoneymoremoreId", account.getData().getAccountMark());
    	 map.put("Remark1", account.getData().getAccountUserId().toString());
    	 map.put("AuthorizeTypeOpen","1,2,3");
    	 Map<String, String> paramsMap = doubleDryService.authorize(map);
    	 
    	 context.put("paramsMap", paramsMap);
    }
}