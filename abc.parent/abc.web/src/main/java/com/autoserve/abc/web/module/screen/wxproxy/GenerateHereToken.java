package com.autoserve.abc.web.module.screen.wxproxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class GenerateHereToken {
	@Resource
	private WxProxyService wxProxyService;
	
 private final static Logger logger = LoggerFactory.getLogger(GenerateHereToken.class);
 public JsonPlainVO<Map<String, String>> execute(Context context, ParameterParser params) {
	   PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
       Map<String, String> param = new HashMap<String, String>();
       String token = getCharAndNumr(8);
       String backUrl =SystemGetPropeties.getStrString("wxBackUrl");
       
       return ResultMapper.toPlainVO(result);	
  }
	 
	 
	 
	 public static String getCharAndNumr(int length)     
	 {     
	     String val = "";     
	              
	     Random random = new Random();     
	     for(int i = 0; i < length; i++)     
	     {     
	         String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	         if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	         {     
	             int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
	             val += (char) (choice + random.nextInt(26));     
	         }     
	         else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	         {     
	             val += String.valueOf(random.nextInt(10));     
	         }     
	     }     
	              
	     return val;     
	 }   
	       
}
