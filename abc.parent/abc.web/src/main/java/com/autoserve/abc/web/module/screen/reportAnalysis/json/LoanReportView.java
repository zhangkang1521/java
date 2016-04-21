package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

public class LoanReportView {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanReportView.class);
	@Resource
	private LoanQueryService loanQueryService;

	public JsonPageVO<LoanReportDO> execute(ParameterParser params) {
		String model = params.getString("model");
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		try {
			if (model != null) {

			}
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
		JsonPageVO<LoanReportDO> resultVO = new JsonPageVO<LoanReportDO>();
		Integer rows = params.getInt("rows");
		Integer page = params.getInt("page");
		PageCondition pageCondition = new PageCondition(page, rows);
		PageResult<LoanReportDO> result = loanQueryService
				.queryLoanReport(pageCondition);
		
		
		resultVO.setTotal(result.getTotalCount());
		resultVO.setRows(result.getData());
		return resultVO;
	}
}
