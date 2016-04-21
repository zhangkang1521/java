package com.autoserve.abc.service.biz.impl.invest;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;

/**
 * 类InvestSetTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月11日 上午9:07:17
 */
public class InvestSetTest extends BaseServiceTest {

    @Resource
    private InvestSetService investSetService;

    @Test
    public void test() {

        InvestSet investSet = new InvestSet();
        investSet.setIsOpen(InvestSetOpenState.STATE_ENABLE);
        this.investSetService.queryInvest(investSet);

    }
}
