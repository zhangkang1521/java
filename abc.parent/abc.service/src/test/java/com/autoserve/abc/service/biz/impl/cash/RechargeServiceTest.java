/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类RechargeServiceTest.java的实现描述：充值测试
 * 
 * @author J.YL 2014年12月6日 上午11:21:51
 */
@Test
public class RechargeServiceTest extends BaseServiceTest {

    @Resource
    private RechargeService recharge;

    @Test
    public void rechargeTest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("RechargeType", "13213");
        params.put("FeeType", "1231");
        params.put("CardNo", "1231");

        PlainResult<DealReturn> result = recharge.recharge(63, UserType.PERSONAL, BigDecimal.valueOf(500), params);
        String url = result.getData().getCashRecords().get(0).getCrRequestUrl();
        String param = result.getData().getCashRecords().get(0).getCrRequestParameter();
        System.out.println(url + "?" + param);
    }
}
