package com.autoserve.abc.web.module.screen.Verification.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 校验是否实名认证、开户、授权
 * 
 * @author DS 2015年1月30日上午10:40:59
 */
public class CheckMoneyMoreMore {
    @Resource
    private HttpSession session;
    @Resource
    private UserService userService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        JsonBaseVO result = new JsonBaseVO();
        User user = (User) session.getAttribute("user");
        String id = params.getString("id");
        if (id == null) {
            id = "";
        }
        if (user != null) {
            PlainResult<UserDO> userDoResult = userService.findById(user.getUserId());
            //实名认证标志
            //			Integer UserRealnameIsproven= userDoResult.getData().getUserRealnameIsproven();
            //真实姓名
            String userRealName = userDoResult.getData().getUserRealName();
            //身份证号
            String userDocNo = userDoResult.getData().getUserDocNo();
            //手机认证
            String mobilePhone ="";
            mobilePhone = userDoResult.getData().getUserPhone();
            //邮箱验证
            String email="";
            email=userDoResult.getData().getUserEmail();
            if (id.equals("3")) {
                if (userRealName == null || "".equals(userRealName) || userDocNo == null || "".equals(userDocNo)) {
                    result.setSuccess(false);
                    result.setMessage("您还没有录入实名认证信息,请先录入！");
                    result.setRedirectUrl("/account/seCenter/realNameCertify");
                } else {
                    result.setSuccess(true);
                    result.setMessage("验证通过！");
                }
            } else if (id.equals("4") || id.equals("5") || id.equals("6")) {
                if (userRealName == null || "".equals(userRealName) || userDocNo == null || "".equals(userDocNo)) {
                    result.setSuccess(false);
                    result.setMessage("您还没有录入实名认证信息,请先录入！");
                    result.setRedirectUrl("/account/seCenter/realNameCertify");
                } else if (userDoResult.getData().getUserBusinessState() == null
                        || userDoResult.getData().getUserBusinessState() < 2) {
                    result.setSuccess(false);
                    result.setMessage("您还未开户，请请先去开户！");
                    result.setRedirectUrl("/account/myAccount/bindAccount");
                } else {
                    result.setSuccess(true);
                    result.setMessage("验证通过！");
                }
            } else {
                if (userRealName == null || "".equals(userRealName) || userDocNo == null || "".equals(userDocNo)) {
                    result.setSuccess(false);
                    result.setMessage("您还没有录入实名认证信息,请先录入！");
                    result.setRedirectUrl("/account/seCenter/realNameCertify");
                }
                else if(mobilePhone == null || "".equals(mobilePhone)){
                	result.setSuccess(false);
                    result.setMessage("您还未进行手机验证，请验证手机！");
                    result.setRedirectUrl("/account/seCenter/phoneVerify");
                }else if(email == null || "".equals(email)){
                	result.setSuccess(false);
                    result.setMessage("您还未进行邮箱验证，请验证邮箱！");
                    result.setRedirectUrl("/account/seCenter/emailVerify");
                }else if (userDoResult.getData().getUserBusinessState() == null
                        || userDoResult.getData().getUserBusinessState() < 2 ) {
                    if(!id.equals("7")){
                	result.setSuccess(false);
                    result.setMessage("您还未开户，请先去开户！");
                    result.setRedirectUrl("/account/myAccount/openAccount");
                    }
                    if(id.equals("8")){
                    	result.setSuccess(true);
                    }
                   
                } else {
                    result.setSuccess(true);
                    result.setMessage("验证通过！");
                }
            }
        } else {
            result.setSuccess(false);
            result.setMessage("您还没有登录,请先登录");
            result.setRedirectUrl("/login/login");
        }
        return result;

    }
}
