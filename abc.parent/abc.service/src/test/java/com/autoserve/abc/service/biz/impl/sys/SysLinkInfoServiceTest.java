package com.autoserve.abc.service.biz.impl.sys;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class SysLinkInfoServiceTest extends BaseServiceTest {
    @Resource
    private SysLinkInfoService sysLinkInfoService;

    @Test
    public void testCreateSyslinkInfo() {
        SysLinkInfoDO sysLinkInfo = new SysLinkInfoDO();
        sysLinkInfo.setSlTitle("标题");
        sysLinkInfo.setSlLogo("logo");
        sysLinkInfo.setSlAddress("url");
        sysLinkInfo.setSlMark("描述");
        this.sysLinkInfoService.createSyslinkInfo(sysLinkInfo);
    }

    //@Test
    public void testRemoveSyslinkInfo() {
        this.sysLinkInfoService.removeSyslinkInfo(2);
    }

    //@Test
    public void testModifyloanAndQuery() {
        PlainResult<SysLinkInfoDO> result = this.sysLinkInfoService.queryById(3);
        SysLinkInfoDO sysLinkInfo = result.getData();
        sysLinkInfo.setSlTitle("修改标题");
        this.sysLinkInfoService.modifySyslinkInfo(sysLinkInfo);
    }

    //@Test
    public void testFindListByParam() {
        PageCondition pageCondition = new PageCondition();
        PageResult<SysLinkInfoDO> result = this.sysLinkInfoService.queryListByParam(pageCondition);
        System.out.println(result.getDataSize());
    }
}
