package com.autoserve.abc.service.biz.impl.mon;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类MonBankInfoServiceTest.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午2:25:06
 */
public class MonBankInfoServiceTest extends BaseServiceTest {
    @Autowired
    private MonBankInfoService monBankInfoService;

    // @Test
    public void testCreateMonBankInfo() {
        MonBankInfo monBankInfo = new MonBankInfo();
        monBankInfo.setMonBankId(2);
        monBankInfo.setMonBankName("中国银行");
        System.out.println("========================");
        BaseResult result = monBankInfoService.createMonBankInfo(monBankInfo);
        System.out.println(result.getMessage());
    }

    // @Test
    public void testModifyMonBankInfo() {
        MonBankInfo monBankInfo = new MonBankInfo();
        monBankInfo.setMonBankId(1);
        monBankInfo.setMonBankName("农业银行");
        System.out.println("========================");
        BaseResult result = monBankInfoService.modifyMonBankInfo(monBankInfo);
        System.out.println(result.getMessage());
    }

    //@Test
    public void testRemoveMonBankInfo() {
        BaseResult result = monBankInfoService.removeMonBankInfo(1);
        System.out.println(result.getMessage());
    }

    // @Test
    public void testFindById() {
        BaseResult result = monBankInfoService.queryById(2);
        System.out.println(result.getMessage());
    }

    @Test
    public void testFindListById() {
        PageResult<MonBankInfoDO> result = monBankInfoService.queryList(new MonBankInfoDO(), new PageCondition());
        System.out.println(result.getMessage());
    }
}
