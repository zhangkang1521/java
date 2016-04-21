package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class InvestorsReportExcel {
	@Resource
	private InvestQueryService investQueryService;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	PageResult<InvestorsReportDO> result = investQueryService.queryInvestorsReport( new PageCondition(0, investQueryService.queryCountInvestorsReport()));
    	
		List<String> fieldName=Arrays.asList(new String[]{"项目代码","投资人用户名","姓名","身份证号","手机号","邮箱","推荐人","投资时间","投资金额","应收利息","已收利息","已收本金","借款到期日","是否转让","是否结清"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(InvestorsReportDO report:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(report.getLoan_no());
			temp.add(report.getUser_name());
			temp.add(report.getUser_real_name());
			temp.add(report.getUser_doc_no());
			temp.add(report.getUser_phone());
			temp.add(report.getUser_email());
			temp.add(report.getRecommend_name());
			temp.add(report.getIn_createtime());
			temp.add(report.getIn_invest_money());
			temp.add(report.getPay_interest());
			temp.add(report.getCollect_interest());
			temp.add(report.getCollect_capital());
			temp.add(report.getLoan_expire_date());
			temp.add(report.getIn_invest_state());
			temp.add(report.getIncome_end());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"项目投资人明细表.xls");

    }
    //导出
    public void ExportExcel(List<?> fieldName,List<?> fieldData,String name){
    	ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator(fieldName, fieldData);
		try {
			response.setCharacterEncoding("gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("GB2312"),"iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			excelFileGenerator.expordExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
