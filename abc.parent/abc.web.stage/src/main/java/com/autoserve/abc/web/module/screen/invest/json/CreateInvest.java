/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.invest.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 创建投资
 *
 * @author segen189 2015年1月13日 下午3:31:24
 */
public class CreateInvest {
    private static final Logger log = LoggerFactory.getLogger(CreateInvest.class);

    @Resource
    private InvestService       investService;

    @Resource
    private InvestQueryService  investQueryService;

    @Resource
    private DealRecordService   dealRecordService;
    @Resource
    private HttpSession session;
    @Resource
	private UserService userService;
    @Autowired
	private DeployConfigService deployConfigService;
    @Resource
	private HttpServletRequest request;
    public JsonBaseVO execute(Context context, ParameterParser params,Navigator nav) {
        try {
            // 投资
	  		  User user = (User) session.getAttribute("user");
	  		  String passw = params.getString("passw");
	  		  if(user!=null){      //校验交易密码
	  				PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
	  				if(passw==null || !CryptUtils.md5(passw).equals(userDoResult.getData().getUserDealPwd())){
	  					BaseResult baseResult=new BaseResult();
	  					baseResult.setSuccess(false);
	  					baseResult.setMessage("交易密码不正确!");
	  					return directReturn(baseResult);
	  				}				
	  		  }else{
	  			nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return null;
	  		  }
	  		  
	  		  Integer loanId  =  params.getInt("loanId");
			  Integer flagLoan = params.getInt("flagLoan");
			  //获取红包参数
			  List<Integer> reds=new ArrayList<Integer>();
			  int[] redsTemp=params.getInts("red", null);
			  if(redsTemp!=null){
				  for(int i=0;i<redsTemp.length;i++){
					  reds.add(redsTemp[i]);
				  }
			  }
			  BidType bidType = BidType.COMMON_LOAN;
	  		  if(loanId!=null && flagLoan ==1){       //普通标
			  		bidType = BidType.COMMON_LOAN;
	  		  }
	  		  if(loanId!=null && flagLoan ==2){    //转让标
				  	 bidType = BidType.TRANSFER_LOAN;
	  		  }
	
            int userId = user.getUserId();
            if (bidType == null) {
                bidType = BidType.BUY_LOAN;
            }

            int bidId =params.getInt("loanId");
            Invest inv = new Invest();
            inv.setUserId(userId);
            inv.setBidType(bidType);     //设置标的类型

            if (BidType.COMMON_LOAN.equals(bidType)) 
            {
                inv.setBidId(bidId);
                inv.setOriginId(bidId);
            } else {
                inv.setBidId(bidId);     
            }

            Double investMoney=params.getDouble("investedMoney");
            if (investMoney!=null) 
            {
                inv.setInvestMoney(BigDecimal.valueOf(investMoney));
            }

            PlainResult<Integer> investCreateResult = investService.createInvest(inv, reds);
            if (!investCreateResult.isSuccess()) 
            {
                return directReturn(investCreateResult);
            }

            	// 支付
            if (BidType.BUY_LOAN.equals(inv.getBidType())) {
                return JsonBaseVO.SUCCESS;
            } else {
                PlainResult<Invest> investQueryResult = investQueryService.queryById(investCreateResult.getData());
                if (!investQueryResult.isSuccess()) {
                    return directReturn(investQueryResult);
                }
                BaseResult invokeResult = new BaseResult();
                if (investCreateResult.isSuccess()) {
                    invokeResult.setSuccess(true);
                    invokeResult.setMessage("投资成功");
                }
                return ResultMapper.toBaseVO(invokeResult);
            }
        } catch (Exception e) {
            log.error("投资失败", e);
        }

        return directReturn(new BaseResult().setError(CommonResultCode.BIZ_ERROR, "投资失败"));
    }
   
    private JsonBaseVO directReturn(BaseResult serviceResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(serviceResult.getMessage());
        result.setSuccess(serviceResult.isSuccess());
        return result;
    }

}
