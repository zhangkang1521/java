/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;

/**
 * 类ToCashServiceTest.java的实现描述：提现测试
 * 
 * @author J.YL 2014年12月9日 上午11:05:54
 */
@Test
public class ToCashServiceTest extends BaseServiceTest {
    @Autowired
    private ToCashService tocash;

    public void toCashTest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("FeeRate", "123");
        params.put("CardType", "456");
        params.put("BankCode", "4532");
        params.put("BranchBankName", "42141");
        params.put("Province", "421");
        params.put("City", "142");

        tocash.toCashOther(63, UserType.PERSONAL, BigDecimal.valueOf(0.01), params);
    }

}
