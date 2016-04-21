package com.autoserve.abc.service.biz.impl.invest;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;

/**
 * 类InvestQueryTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年1月27日 上午10:06:15
 */
public class InvestQueryTest extends BaseServiceTest {

    @Resource
    private InvestQueryService investQueryService;

    @Test
    public void test() {
        InvestSearchDO search = new InvestSearchDO();
        search.setUserId(1);
        search.setEndDate(new Date());

        System.out.println(this.investQueryService.queryInvestList(search, new PageCondition()));
    }
}
