package com.autoserve.abc.web.module.screen.cashsq.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashSqDO;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.dao.dataobject.UserRecommendDO;
import com.autoserve.abc.service.biz.convert.CashSqConverter;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.CashSq;
import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cashsq.CashSqService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.CreditVOConverter;
import com.autoserve.abc.web.module.screen.selfprove.json.AccountManagementListView;
import com.autoserve.abc.web.module.screen.system.json.EditMenu;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.JsonPageVO;

public class CashSqListView {
	 private static Logger logger = LoggerFactory.getLogger(CashSqListView.class);

	    @Resource
	    private UserService   userService;
	    @Resource
	    private CashSqService   cashSqService;
	    public JsonPageVO<CashSqDO> execute(ParameterParser params) {
	    	logger.info("查询免费提现额度申请");
	        Integer rows = params.getInt("rows");
	        Integer page = params.getInt("page");
	        PageCondition pageCondition = new PageCondition(page, rows);
	        CashSqDO cashSqDO = new CashSqDO();
 
	        PageResult<CashSqDO> pageResult = cashSqService.queryListCashSq(cashSqDO, pageCondition);
	        
	      
	        
	        return ResultMapper.toPageVO(pageResult);
	    }
}
