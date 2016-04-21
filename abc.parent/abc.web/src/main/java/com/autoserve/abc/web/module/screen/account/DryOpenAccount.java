package com.autoserve.abc.web.module.screen.account;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class DryOpenAccount {
    @Autowired
    private UserService        userservice;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DoubleDryService   doubleDryService;

    public void execute(@Param("empId") int empId, Context context, Navigator nav, ParameterParser params) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("RegisterType", "2");
        map.put("AccountType", "1");
        map.put("Mobile", params.getString("Mobile"));
        map.put("Email", params.getString("Email"));
        map.put("RealName", params.getString("RealName"));
        map.put("IdentificationNo", params.getString("IdentificationNo"));
        map.put("LoanPlatformAccount", params.getString("empName"));
        map.put("Remark1", params.getString("empId"));
        PlainResult<Map> paramMap = doubleDryService.openAccent(map);
        Map jo = paramMap.getData();
        String submitUrl = jo.get("SubmitURL").toString();
        context.put("paramMap", paramMap);
        PlainResult<JSONObject> jsonObject = new PlainResult<JSONObject>();
        jsonObject.setMessage(jo.toString());
        jsonObject.setSuccess(true);
        /* return ResultMapper.toPlainVO(jsonObject); */

    }
}
