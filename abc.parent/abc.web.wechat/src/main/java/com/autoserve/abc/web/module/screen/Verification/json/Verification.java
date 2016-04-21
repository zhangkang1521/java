package com.autoserve.abc.web.module.screen.Verification.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 验证用户名，手机号码...
 * 
 * @author DS
 */
public class Verification {
    @Resource
    private UserService         userService;
    @Resource
    private HttpSession         session;
    @Autowired
    private DeployConfigService deployConfigService;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private RealnameAuthService realnameAuthService;

    public JsonBaseVO execute(Context context, ParameterParser params, Navigator nav) {
        JsonBaseVO result = new JsonBaseVO();
        UserDO userDO = new UserDO();
        //标志
        String flag = params.getString("flag");
        //用户名flag为1
        if (flag != null && "1".equals(flag)) {
            String userName = params.getString("userName");
            userDO.setUserName(userName);
            userDO.setUserState(-2);//特殊状态,当是此状态则查询不限定用户状态
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() != 0) {
                result.setSuccess(false);
                result.setMessage("用户名不能重复!");
            } else {
                result.setSuccess(true);
            }

        }
        if (flag != null && "2".equals(flag)) {
            //手机号码
            String userPhone = params.getString("userPhone");
            userDO.setUserPhone(userPhone);
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() != 0) {
                result.setSuccess(false);
                result.setMessage("手机号码不能重复!");
            } else {
                result.setSuccess(true);
            }
        }

        //邮箱flag为3
        if (flag != null && "3".equals(flag)) {
            //邮箱
            String userEmail = params.getString("userEmail");
            userDO.setUserEmail(userEmail);
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() != 0) {
                result.setSuccess(false);
                result.setMessage("邮箱不能重复!");
            } else {
                result.setSuccess(true);
            }
        }

        //身份证号flag为4
        if (flag != null && "4".equals(flag)) {
            //身份证号
            String userDocNo = params.getString("userDocNo");
            userDO.setUserDocNo(userDocNo);
            userDO.setUserRealnameIsproven(1);//已被实名验证后的，否则自己改成自己原来的身份证都保存不了
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() != 0) {
                result.setSuccess(false);
                result.setMessage("身份证不能重复!");
            } else {
                result.setSuccess(true);
            }
        }

        //交易密码flag为5
        if (flag != null && "5".equals(flag)) {
            String userDealPwd = params.getString("userDealPwd");
            User user = (User) session.getAttribute("user");
            if (user == null) {
                nav.redirectToLocation(deployConfigService.getLoginUrl(request));
                return null;
            }
            userDO.setUserId(user.getUserId());
            userDO.setUserDealPwd(CryptUtils.md5(userDealPwd));
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() == 0) {
                result.setSuccess(false);
                result.setMessage("交易密码错误!");
            } else {
                result.setSuccess(true);
            }
        }
        //登录密码flag为6
        if (flag != null && "6".equals(flag)) {
            String userPwd = params.getString("userPwd");
            User user = (User) session.getAttribute("user");
            if (user == null) {
                nav.redirectToLocation(deployConfigService.getLoginUrl(request));
                return null;
            }
            userDO.setUserId(user.getUserId());
            userDO.setUserPwd(CryptUtils.md5(userPwd));
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() == 0) {
                result.setSuccess(false);
                result.setMessage("登录密码错误!");
            } else {
                result.setSuccess(true);
            }
        }
        //真实姓名flag为7
        if (flag != null && "7".equals(flag)) {
            String userRealName = params.getString("userRealName");
            userDO.setUserRealName(userRealName);
            PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
            if (pageResult.getData().size() != 0) {
                result.setSuccess(false);
                result.setMessage("真实姓名不能重复!");
            } else {
                result.setSuccess(true);
            }

        }

        //实名认证flag为8
        if (flag != null && "8".equals(flag)) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                nav.redirectToLocation(deployConfigService.getLoginUrl(request));
                return null;
            }
            BaseResult baseResult = realnameAuthService.realNameAudit(user.getUserId());
            result.setSuccess(baseResult.isSuccess());
            result.setMessage(baseResult.getMessage());
            result.setRedirectUrl("/account/myAccount/openAccount");
        }
        return result;
    }

}
