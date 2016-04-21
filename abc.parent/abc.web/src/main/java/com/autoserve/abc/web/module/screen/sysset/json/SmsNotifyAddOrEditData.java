package com.autoserve.abc.web.module.screen.sysset.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 参数设置 短信设置 类MessageAddOrEditData.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月10日 下午5:11:11
 */
public class SmsNotifyAddOrEditData {
    @Resource
    private SysConfigService sysConfigService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult result = new BaseResult();
//        List<SysConfig> list = new ArrayList<SysConfig>();
//        String sys_user_name = params.getString("sys_user_name");
//        String sys_user_pwd = params.getString("sys_user_pwd");
//        
//        SysConfig sysConfigName = new SysConfig();
//        sysConfigName.setConf(SysConfigEntry.SMS_USER);
//        sysConfigName.setConfValue(sys_user_name);
//        list.add(sysConfigName);
//        SysConfig sysConfigPwd = new SysConfig();
//        sysConfigPwd.setConf(SysConfigEntry.SMS_PASSWORD);
//        sysConfigPwd.setConfValue(sys_user_pwd);
//        list.add(sysConfigPwd);
//
//        for (SysConfig sysCofig : list) {
//            result = this.SysConfigService.modifySysConfig(sysCofig.getConf(), sysCofig.getConfValue());
//        }
        
        Integer investSwitch = params.getInt("investSwitch");
        String investTemplate = params.getString("investTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_INVEST_CFG, JSON.toJSONString(new SmsNotifyCfg(investSwitch, investTemplate)));
        
        Integer specialSwitch = params.getInt("specialSwitch");
        String specialTemplate = params.getString("specialTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_SPECIAL_TRANSFER_CFG, JSON.toJSONString(new SmsNotifyCfg(specialSwitch, specialTemplate)));
        
        Integer commonSwitch = params.getInt("commonSwitch");
        String commonTemplate = params.getString("commonTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_COMMON_TRANSFER_CFG, JSON.toJSONString(new SmsNotifyCfg(commonSwitch, commonTemplate)));
        
        Integer repaymentSwitch = params.getInt("repaymentSwitch");
        String repaymentTemplate = params.getString("repaymentTemplate");
        sysConfigService.modifySysConfig(SysConfigEntry.SMS_NOTIFY_REPAYMENT_CFG, JSON.toJSONString(new SmsNotifyCfg(repaymentSwitch, repaymentTemplate)));
        
        return ResultMapper.toBaseVO(result);
    }
}
