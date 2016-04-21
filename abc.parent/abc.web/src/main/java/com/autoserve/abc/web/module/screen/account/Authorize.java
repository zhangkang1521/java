package com.autoserve.abc.web.module.screen.account;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;

public class Authorize {
    @Autowired
    private UserService        userservice;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DoubleDryService   doubleDryService;

    public void execute(@Param("empId") int empId, @Param("accountMark") String accountMark, Context context,
                        Navigator nav, ParameterParser params) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("MoneymoremoreId", accountMark);
        paramsMap.put("AuthorizeTypeOpen", "1,2,3");
        paramsMap.put("Remark1", String.valueOf(empId));
        Map<String, String> resultMap = doubleDryService.authorize(paramsMap);
        context.put("paramsMap", resultMap);

    }
}
