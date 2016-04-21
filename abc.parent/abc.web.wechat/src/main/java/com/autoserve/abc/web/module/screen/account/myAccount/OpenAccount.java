package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OpenAccount {

    @Autowired
    private UserService            userService;
    @Autowired
    private HttpSession            session;
    @Resource
    private HuifuPayService        huifuPayService;
    @Resource
    private CompanyCustomerService companyCustomerService;
    @Autowired
    private DoubleDryService doubledryservice;
    @Autowired
    private RealnameAuthService realnameAuthService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        User user = (User) session.getAttribute("user");
        PlainResult<User> result = userService.findEntityById(user.getUserId());
        
        //个人用户开户前实名认证
        if(result.getData().getUserType().equals(UserType.PERSONAL)){
	   	 	BaseResult baseResult = realnameAuthService.realNameAudit(user.getUserId());
	   	 	if(!baseResult.isSuccess()){
	   	 		context.put("Message", baseResult.getMessage());
	   	 		nav.forwardTo("/error.vm");
	   	 	}
        }
        
        session.setAttribute("user", result.getData());
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
