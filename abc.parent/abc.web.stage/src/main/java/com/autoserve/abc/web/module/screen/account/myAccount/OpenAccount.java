package com.autoserve.abc.web.module.screen.account.myAccount;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class OpenAccount {
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Autowired
    private UserService         userservice;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
    private DoubleDryService doubledryservice;
	
	@Resource
	private HttpServletRequest request;
	
	@Resource
	private RealnameAuthService realnameAuthService;
    
	@Resource
	private CompanyCustomerService companyCustomerService;
	
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	PlainResult<User> result= userservice.findEntityById(user.getUserId());
    	User userResult=result.getData();
    	//判断是否进行实名认证(个人用户)
    	if(userResult.getUserType()==UserType.PERSONAL &&
    		(userResult.getUserRealnameIsproven()==null || userResult.getUserRealnameIsproven()==0)){
    		context.put("ResultCode", "500");
        	context.put("Message", "没有进行实名认证，不能开户!");
        	nav.forwardTo("/error").end();
    	}
    	session.setAttribute("user", userResult);
    	Map<String,String> param= new HashMap<String,String>();
    	param.put("RegisterType", "2");
    	if(result.getData().getUserType()==null||"".equals(result.getData().getUserType())||result.getData().getUserType()==UserType.PERSONAL){
    		param.put("AccountType", "");
    		param.put("IdentificationNo", result.getData().getUserDocNo());
    		param.put("RealName", result.getData().getUserRealName());
    		param.put("Mobile", result.getData().getUserPhone());
        	param.put("Email", result.getData().getUserEmail());
    	}else{
    		CompanyCustomerDO company = companyCustomerService.findByUserId(user.getUserId()).getData();
    		param.put("AccountType","1");
    		param.put("IdentificationNo", company.getCcLicenseNo());
    		param.put("RealName", company.getCcCompanyName());
    		param.put("Mobile", company.getCcContactPhone());
        	param.put("Email", company.getCcContactEmail());
    	}   	
    	param.put("LoanPlatformAccount", result.getData().getUserName());
    	param.put("Remark1", result.getData().getUserId().toString());
    	PlainResult<Map> paramMap = doubledryservice.openAccent(param);
    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("paramMap", paramMap);
    }
}
