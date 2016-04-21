package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

public class UserInvestInfoReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(UserInvestInfoReportView.class);
	@Resource
	private UserService userService;

	public JsonPageVO<UserInvestInfoReportDO> execute(ParameterParser params) {
		String model = params.getString("model");
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		UserDO userDO = new UserDO();
		try {
			if (model != null) {

			}
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
		JsonPageVO<UserInvestInfoReportDO> resultVO = new JsonPageVO<UserInvestInfoReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<UserInvestInfoReportDO> result = userService
				.queryUserInvestInfo(userDO,pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
