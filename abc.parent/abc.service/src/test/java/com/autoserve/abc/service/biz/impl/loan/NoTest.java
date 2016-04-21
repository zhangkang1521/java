package com.autoserve.abc.service.biz.impl.loan;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class NoTest extends BaseServiceTest {

    @Resource
    private NumberRuleService numberRuleService;

    @Test
    public void test() {

        PlainResult<String> result = this.numberRuleService.createNumberByYear();

        System.out.println(result.getData());
    }

}
