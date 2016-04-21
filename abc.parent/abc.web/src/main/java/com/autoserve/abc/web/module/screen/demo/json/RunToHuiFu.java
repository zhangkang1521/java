package com.autoserve.abc.web.module.screen.demo.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

public class RunToHuiFu {
	 @Resource 
	    private PayMentService      payMent;

	    public void  execute(Context context, ParameterParser params) {

	                PlainResult<Map<String,String>> invokeResult = new  PlainResult<Map<String,String>>();
	                List<DealRecordDO> dealRecords =  new ArrayList<DealRecordDO>();
	                invokeResult =  payMent.tranfulAll(params.getString("seq"), dealRecords);
	            	context.put("jo", invokeResult.getData());
	    }

}
