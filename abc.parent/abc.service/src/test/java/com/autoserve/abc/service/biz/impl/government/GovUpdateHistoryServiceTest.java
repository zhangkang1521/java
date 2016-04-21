package com.autoserve.abc.service.biz.impl.government;

import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.government.GovUpdateHistoryService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/11/19 17:30.
 */
public class GovUpdateHistoryServiceTest extends BaseServiceTest {

    @Autowired
    private GovUpdateHistoryService service;

    @Test
    public void testQueryList() {
        GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
        historyDO.setGuhGovid(22);
        PageResult<GovernmentUpdateHistoryDO> page = service.queryList(historyDO, new PageCondition());
        System.out.println("~~~~~~~~~~~~" + page.getDataSize());
    }

    @Test
    public void testFindOldAndNewValue() {
        PlainResult<JSONObject> result = service.findOldAndNewValue(11);
        JSONObject jsonObject = result.getData();
        System.out.println("!!!!!!!!!!!!!!!!!!!!" + jsonObject.toJSONString());
    }

    @Test
    public void testFindLastUpdateHistoryList() {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(14);
        ListResult<GovernmentUpdateHistoryDO> result = service.findLastUpdateHistoryList(ids);
        System.out.println(result.getData().size());
    }
}
