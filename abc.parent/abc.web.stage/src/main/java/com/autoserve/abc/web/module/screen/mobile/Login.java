package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.IPUtil;

/**
 * 手机端用户登录
 * 
 * @author Bo.Zhang
 *
 */
public class Login {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		// /mobile/login.json?username=zhangkang100&password=123456
		JsonMobileVO result = new JsonMobileVO();

		try {
			String loginValue = params.getString("username");
			String password = params.getString("password");

			if (StringUtil.isBlank(loginValue) || StringUtil.isBlank(password)) {
				result.setResultCode("201");
				result.setResultMessage("登录名或密码不能为空");
				return result;
			}

			// 尝试用户名
			PlainResult<User> findResult = userService.login(loginValue,
					CryptUtils.md5(password), IPUtil.getUserIpAddr(request));
			if (findResult.isSuccess()) {
				User user = findResult.getData();
				return success(user);
			} else {
				result.setResultCode("201");
				result.setResultMessage(findResult.getMessage());
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("error");
		}

		return result;
	}
	
   
	
	
	
	
	

	private JsonMobileVO success(User user) {
		JsonMobileVO result = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
//		objMap.put("userId", user.getUserId());
		objMap.put("userId", user.getUserUuid());   //插入手机端为uuid
		objMap.put("userName", user.getUserName());
		if (user.getUserType() != null) {
			objMap.put("userType", user.getUserType().getType());
		}
		objMap.put("userEmail", MobileHelper.nullToEmpty(user.getUserEmail()));
		objMap.put("userRealName", MobileHelper.nullToEmpty(user.getUserRealName()));
		objMap.put("userDocNo", MobileHelper.nullToEmpty(user.getUserDocNo()));
		objMap.put("userPhone", user.getUserPhone());
		objMap.put("userHeadImg", MobileHelper.nullToEmpty(user.getUserHeadImg()));
		objMap.put("phoneValidFlag", user.getUserMobileIsbinded().getState());
		objMap.put("emailValidFlag", user.getUserEmailIsbinded().getState());
		if(user.getUserBusinessState()!=null && user.getUserBusinessState().state > 1){
			objMap.put("isOpenAccount", "1");
		}else {
			objMap.put("isOpenAccount", "0");
		}
		result.setResultCode("200");
		result.setResultMessage("登录成功");
		result.setResult(JSON.toJSON(objMap));
		return result;
	}

}
