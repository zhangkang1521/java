package com.autoserve.abc.web.module.screen.wxproxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.WxTokenDO;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class GenerateToken {
	@Resource
	private WxProxyService wxProxyService;
	 private final static Logger logger = LoggerFactory.getLogger(GenerateToken.class);
	 public void execute(Context context, ParameterParser params) {
		  // PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
/*	       Map<String, String> param = new HashMap<String, String>();
	       String mainJsonStr = params.getString("tokenId");
           JSONObject mainJson = JSON.parseObject(mainJsonStr);
*/	       String AppID = params.getString("AppID");
	       String AppSecret = params.getString("AppSecret");
	       //result = wxProxyService.generateToken(AppID, AppSecret);
	       PlainResult<WxTokenDO> result =  wxProxyService.CreatToken(AppID, AppSecret);
	       WxTokenDO wxTokenDO = result.getData();
	       context.put("accessToken", wxTokenDO.getWxAccessToken());
	       context.put("token", wxTokenDO.getWxToken());
	       context.put("state", wxTokenDO.getWxState());
	       context.put("appId", wxTokenDO.getWxOpenId());
	       
	  }
	       
}
