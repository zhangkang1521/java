package com.autoserve.abc.service.biz.impl.bankinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;

public class BankInfoTest extends BaseServiceTest {

    @Autowired
    private BankInfoService bankInfoService;

    @Test
    public void test() {
        System.out.println(bankInfoService.queryBankInfo("1234", "12456778"));
    }
}
