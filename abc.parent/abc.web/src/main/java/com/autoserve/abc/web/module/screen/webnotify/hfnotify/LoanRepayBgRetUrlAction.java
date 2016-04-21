package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.ListResult;

import org.apache.commons.lang.StringUtils;

public class LoanRepayBgRetUrlAction {
	@Resource 
	private HttpSession session;
	@Resource 
	private HuifuPayService paySer;
	@Resource
	private BankInfoService bankInfoSer;
	@Resource
	private AccountInfoService accountInfoSer;
	@Resource
	private HttpServletRequest req;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private IncomePlanService incomePlanService;
	
	public void execute(Context context,ParameterParser params,Navigator nav){
    	
		Map<String,String> map=paySer.repaymentBgRetUrl(req);
		
		if(map.get("RespCode").equals("000")){
//			int incomePlanId = Integer.valueOf(map.get("MerPriv").toString());
//			//修改收益计划表
//			ListResult<IncomePlan> incomeList = incomePlanService.queryIncomePlanById(incomePlanId);
//			IncomePlanState incomePlanState = incomeList.getData().get(0).getIncomePlanState();
//			System.out.println("incomePlanState:"+incomePlanState);
//	        incomePlanService.modifyIncomePlanById(incomePlanId, IncomePlanState.GOING, IncomePlanState.CLEARED);
		}
		String RecvOrdId = map.get("RecvOrdId").toString();
		try {
            if (StringUtils.isNotBlank(RecvOrdId)) {
                PrintWriter out = resp.getWriter();
                out.print(RecvOrdId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }
}
