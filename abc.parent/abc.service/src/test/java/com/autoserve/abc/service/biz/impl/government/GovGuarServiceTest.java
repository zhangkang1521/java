package com.autoserve.abc.service.biz.impl.government;

import com.autoserve.abc.dao.dataobject.GovGuarDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.government.GovGuarService;
import com.autoserve.abc.service.biz.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/10 16:30.
 */
public class GovGuarServiceTest extends BaseServiceTest {
    @Autowired
    private GovGuarService govGuarService;

    @Test
    public void testInsertGovGuarByList() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        Integer govId = 1;
        BaseResult result = govGuarService.insertGovGuarByList(list, govId);
        System.out.println(result.getMessage());
    }

    @Test
    public void testBatchInsert(){
        List<GovGuarDO> govGuarDOs = new ArrayList<GovGuarDO>();
        GovGuarDO govGuarDO = new GovGuarDO();
        govGuarDO.setGgGovId(100);
        govGuarDO.setGgGuarId(101);
        govGuarDO.setGgGuarAmount(BigDecimal.ONE);
        govGuarDOs.add(govGuarDO);
        govGuarDO = new GovGuarDO();
        govGuarDO.setGgGovId(100);
        govGuarDO.setGgGuarId(200);
        govGuarDO.setGgGuarAmount(BigDecimal.ZERO);
        govGuarDOs.add(govGuarDO);

        govGuarService.createByDOList(govGuarDOs);
    }
}
