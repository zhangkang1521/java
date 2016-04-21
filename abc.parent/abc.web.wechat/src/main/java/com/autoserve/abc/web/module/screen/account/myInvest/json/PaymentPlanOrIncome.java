package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.ListResult;
/**
 * 
 * @author DS
 *
 * 2015年2月5日上午11:56:02
 */
public class PaymentPlanOrIncome {
	@Resource
	private IncomePlanService incomePlanService;
	public  ListResult<IncomePlanVO> execute(Context context, ParameterParser params,Navigator nav) {
		ListResult<IncomePlanVO> listResult=new ListResult<IncomePlanVO>();
		List<IncomePlanVO> listvo=new ArrayList<IncomePlanVO>();
		Integer loanId=params.getInt("loanId");
		Integer userId=params.getInt("UserId");
		Integer investId=params.getInt("investId");
		String flag=params.getString("flag");
		IncomePlan incomePlan=new IncomePlan();
		incomePlan.setLoanId(loanId);
		incomePlan.setBeneficiary(userId);
		incomePlan.setInvestId(investId);
	    ListResult<IncomePlan> list=incomePlanService.queryIncomePlanList(incomePlan);
	    listResult.setSuccess(list.isSuccess());
	    listResult.setMessage(list.getMessage());
	    if(list.getData().size()!=0){
	    	for(int i=0;i<list.getData().size();i++){
	    		IncomePlanVO incomePlanVO=new IncomePlanVO();
	    		IncomePlan incomePlan2=list.getData().get(i);
	    		incomePlanVO.setPaytimeStr(new SimpleDateFormat("yyy-MM-dd").format(incomePlan2.getPaytime()));
	    		incomePlanVO.setLoanPeriod(incomePlan2.getLoanPeriod());
//	    		if(incomePlan2.getIncomePlanState().getState()==0){   //待收益
//	    			incomePlanVO.setCollectTotal(incomePlan2.getCollectTotal());
//		    		incomePlanVO.setBackMoney(incomePlan2.getPayTotalMoney().subtract(incomePlan2.getCollectCapital()));
//	    		}else if(incomePlan2.getIncomePlanState().getState()==2){  //已结清
//	    			incomePlanVO.setCollectTotal(incomePlan2.getPayTotalMoney().subtract(incomePlan2.getCollectCapital()));
//		    		incomePlanVO.setBackMoney(incomePlan2.getCollectTotal());
//	    			incomePlanVO.setCollectTotal(incomePlan2.getCollectTotal());
//		    		incomePlanVO.setBackMoney(incomePlan2.getPayTotalMoney().subtract(incomePlan2.getCollectCapital()));
//	    		}	
	    		incomePlanVO.setCollectTotal(incomePlan2.getCollectTotal());
	    		incomePlanVO.setPayTotalMoney(incomePlan2.getPayTotalMoney().subtract(incomePlan2.getCollectTotal()));
	    		//实还本金
	    		incomePlanVO.setCollectCapital(incomePlan2.getCollectCapital());
	    		//实还利息
	    		incomePlanVO.setCollectInterest(incomePlan2.getCollectInterest());
	    		//实还利息
	    		
	    		listvo.add(incomePlanVO);
	    	}
	    	listResult.setData(listvo);
	    }
	    
	    listResult.setMessage(flag);
		return listResult;
	}

}
