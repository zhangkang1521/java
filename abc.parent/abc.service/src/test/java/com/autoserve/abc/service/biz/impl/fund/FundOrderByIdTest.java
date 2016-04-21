/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.fund;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.intf.FundOrderDao;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;

/**
 * 类FundOrderByIdTest.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月24日 下午4:35:25
 */
public class FundOrderByIdTest extends BaseServiceTest {
    @Resource
    private FundOrderDao fundOrder;

    @Test
    public void fundOrderByIdTest() {
        FundOrderApplyUserJDO fundOrderApplyUserJDO = new FundOrderApplyUserJDO();
        fundOrderApplyUserJDO.setFcCheckIdear("44444666juijiujiu");
        fundOrderApplyUserJDO.setFoOrderId(1);
        fundOrderApplyUserJDO.setFcCheckState(2);
        fundOrderApplyUserJDO.setFoOrderState(2);
        fundOrder.updateByOrderd(fundOrderApplyUserJDO);
        System.out.println(fundOrderApplyUserJDO.getFcCheckIdear());

    }
}
