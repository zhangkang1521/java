package com.autoserve.abc.web.module.screen.Verification.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 校验是否实名认证、开户、授权
 * @author DS
 *
 * 2015年1月30日上午10:40:59
 */
public class CheckMoneyMoreMore {
	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Resource
	private CompanyCustomerService companyCustomerService;
	public JsonBaseVO execute(Context context, ParameterParser params){
		 JsonBaseVO result = new JsonBaseVO();
		 User user=(User)session.getAttribute("user");
		 String id = params.getString("id");
		 if(id==null){
			 id="";
		 }
		if(user!=null){
			PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
			//实名认证标志
//			Integer UserRealnameIsproven= userDoResult.getData().getUserRealnameIsproven();
			//真实姓名
			String userRealName=userDoResult.getData().getUserRealName();
			//身份证号
			String userDocNo=userDoResult.getData().getUserDocNo();
			//判断是否是企业用户
			Integer usrtype=userDoResult.getData().getUserType();    //用户类型
			String ccCompanyName="";   //企业名
			String ccLicenseNo="";  //营业执照号
			if(userDoResult.getData()!=null && usrtype!=null &&  usrtype==2){
				PlainResult<CompanyCustomerDO> companyCustomer=companyCustomerService.findByUserId(userDoResult.getData().getUserId());
				ccCompanyName=companyCustomer.getData().getCcCompanyName();
				ccLicenseNo=companyCustomer.getData().getCcLicenseNo();
			}
				if(id.equals("3")){
						if((usrtype==1 && (userRealName==null ||"".equals(userRealName) || userDocNo==null || "".equals(userDocNo))) ||
						(usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}else if(id.equals("4")||id.equals("5")||id.equals("6")){
					if((usrtype==1 && (userRealName==null ||"".equals(userRealName) || userDocNo==null || "".equals(userDocNo))) ||
							(usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
							result.setSuccess(false);
							result.setMessage("您还未开户，请先去开户！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}else{
					if((usrtype==1 && (userRealName==null ||"".equals(userRealName) || userDocNo==null || "".equals(userDocNo))) ||
							(usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
							result.setSuccess(false);
							result.setMessage("您还未开户，请先去开户！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(userDoResult.getData().getUserAuthorizeFlag()==null || userDoResult.getData().getUserAuthorizeFlag()==0){
							result.setSuccess(false);
							result.setMessage("您还未开启自动转账授权，请先去授权！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}
		}else{
			result.setSuccess(false);
			result.setMessage("您还没有登录,请先登录");
			result.setRedirectUrl("/login/login");
		}
		 return result;
		 
	 }
}
