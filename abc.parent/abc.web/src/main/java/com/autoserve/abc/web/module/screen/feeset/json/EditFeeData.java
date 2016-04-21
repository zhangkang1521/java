package com.autoserve.abc.web.module.screen.feeset.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class EditFeeData {
    @Resource
    private FeeSettingService feeSettingService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer sys_fee_id = params.getInt("sys_fee_id");
        Integer sys_fee_type = params.getInt("feeType");
        Integer sys_min_money = params.getInt("sys_min_money");
        Integer sys_max_money = params.getInt("sys_max_money");
        Integer sys_collect_type = params.getInt("sys_collect_type");
        Double sys_fee_rate = params.getDouble("sys_fee_rate");
        Integer sys_product_id = params.getInt("sys_product_id");

        FeeSetting feeSetting = new FeeSetting();
        feeSetting.setId(sys_fee_id);
        feeSetting.setFeeType(FeeType.valueOf(sys_fee_type));
        feeSetting.setMinAmount(BigDecimal.valueOf(sys_min_money));
        feeSetting.setMaxAmount(BigDecimal.valueOf(sys_max_money));
        feeSetting.setChargeType(ChargeType.valueOf(sys_collect_type));
        if(ChargeType.valueOf(sys_collect_type) == ChargeType.BY_DEAL){
        	feeSetting.setAccurateAmount(BigDecimal.valueOf(sys_fee_rate));        	
        }
        if(ChargeType.valueOf(sys_collect_type) == ChargeType.BY_RATIO){
        	feeSetting.setRate(sys_fee_rate);        	
        }
        feeSetting.setLoanCategory(LoanCategory.valueOf(sys_product_id));

        BaseResult result = this.feeSettingService.modifyFeeSetting(feeSetting);
        return ResultMapper.toBaseVO(result);
    }

}
