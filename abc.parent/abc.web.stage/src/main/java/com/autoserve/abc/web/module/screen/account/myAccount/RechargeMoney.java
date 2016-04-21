package com.autoserve.abc.web.module.screen.account.myAccount;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class RechargeMoney {
	@Autowired
    private UserService         userservice;
	@Autowired
    private HttpSession session;
	@Autowired
    private DealRecordService dealrecordservice;
	@Autowired
    private AccountInfoService  accountinfoservice;
	@Autowired
    private RechargeService rechargeservice;

    public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	user = userservice.findEntityById(user.getUserId()).getData();
    	
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||"".equals(user.getUserType())||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	
    	Double money = params.getDouble("money");
    	String mey =params.getString("money");
    	//充值类型
    	String rechargeType=params.getString("RechargeType");
    	//手续费类型
    	String feeType="";
    	if(rechargeType==null){
    		rechargeType="";
    	}else{
    		//充值成功时从充值人账户全额扣除
    		feeType="1";  
    	}
    	
    	if(money!=null&&!"".equals(money)&&account!=null&&!"".equals(account)){
    		
//    	Deal deal = new Deal();
//    	InnerSeqNo innerseqno = InnerSeqNo.getInstance();
//    	deal.setInnerSeqNo(innerseqno);
//    	deal.setBusinessType(DealType.RECHARGE);
//    	deal.setOperator(user.getUserId());
//    	
//    	List<DealDetail> list = new ArrayList<DealDetail>();
//    	DealDetail dealdetail = new DealDetail();
    	//获取开户号
    	String userOpenNo = account.getData().getAccountMark();
    	if(userOpenNo==null||"".equals(userOpenNo)){//尚未开户
    		
    		
    	}
    	
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("RechargeMoneymoremore", userOpenNo);
    	map.put("PlatformMoneymoremore","");
//    	map.put("OrderNo", innerseqno.toString());
    	map.put("Amount", mey);
    	map.put("RechargeType", rechargeType);
    	map.put("FeeType", feeType);
    	map.put("CardNo", "");
    	map.put("RandomTimeStamp", "");
    	map.put("ReturnURL", "");
    	map.put("NotifyURL", "");
    	
    	BigDecimal bigdecimal = new BigDecimal(money);
    	PlainResult<DealReturn> paramMap = rechargeservice.recharge(user.getUserId(), user.getUserType(), bigdecimal, map);
    	
//    	dealdetail.setData(map);
//    	dealdetail.setMoneyAmount(bigdecimal);
//    	dealdetail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
//    	list.add(dealdetail);
//    	
//    	deal.setDealDetail(list);
    	
//    	PlainResult<DealReturn> paramMap = dealrecordservice.createBusinessRecord(deal, null);
//    	System.out.println("发送参数:======"+paramMap.getData().getCashRecords().get(0).getCrRequestParameter());
    	JSONObject jo  = JSON.parseObject(paramMap.getData().getParams());
    	
    	context.put("jo", jo);
    	
    	}
    }
}
