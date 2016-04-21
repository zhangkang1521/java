package com.autoserve.abc.web.module.screen.message.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AddMessageInfo {
	
	@Autowired
	private SysMessageInfoService messageInfoService;
	
	public JsonBaseVO execute(@Params SysMessageInfoDO messageDO) {
		JsonBaseVO vo = new JsonBaseVO();
		LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
		messageDO.setSysUserId(emp.getEmpId());
		messageDO.setSysUserName(emp.getEmpName());
		BaseResult result = this.messageInfoService.createMessage(messageDO);
		if(!result.isSuccess()){
			vo.setSuccess(false);
			vo.setMessage(result.getMessage());
		}
		return vo;
	}
}
