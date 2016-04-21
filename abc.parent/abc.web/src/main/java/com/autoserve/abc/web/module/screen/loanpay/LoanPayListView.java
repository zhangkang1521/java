package com.autoserve.abc.web.module.screen.loanpay;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

public class LoanPayListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
    	context.put("loanCategories", LoanCategory.values());
        GetButtonUtils.getButtons(authorityService, context, params);
    }

}
