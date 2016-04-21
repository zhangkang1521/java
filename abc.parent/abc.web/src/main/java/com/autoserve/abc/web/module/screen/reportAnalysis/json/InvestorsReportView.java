package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

public class InvestorsReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(InvestorsReportView.class);
	@Resource
	private InvestQueryService investQueryService;

	public JsonPageVO<InvestorsReportDO> execute(ParameterParser params) {
		String model = params.getString("model");
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		try {
			if (model != null) {

			}
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
		JsonPageVO<InvestorsReportDO> resultVO = new JsonPageVO<InvestorsReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<InvestorsReportDO> result = investQueryService
				.queryInvestorsReport(pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
