package com.autoserve.abc.service.biz.intf.sys;

import java.util.List;

import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-11-27,14:10
 */
public interface SysConfigService {
    public PlainResult<SysConfig> querySysConfig(SysConfigEntry configEntry);

    public BaseResult modifySysConfig(SysConfigEntry configEntry, String configValue);

    public BaseResult batchCreateSysConfig(List<SysConfig> list);

    public ListResult<SysConfig> queryListByParam(List<String> list);
}
