package com.autoserve.abc.service.biz.impl.government;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author RJQ 2014/11/19 9:59.
 */
public class GovernmentServiceTest extends BaseServiceTest {

    @Autowired
    private GovernmentService governmentService;
    @Autowired
    private AreaService areaService;

    @Test
    public void testCreateGovernment() {
        GovPlainJDO govPlainJDO = new GovPlainJDO();
        govPlainJDO.setGovName("xiaodai1111111");
        govPlainJDO.setGovUserName("xiaodai1111111");
        govPlainJDO.setGovIsOfferGuar(0);
        govPlainJDO.setGovMaxLoanAmount(new BigDecimal(100));
        govPlainJDO.setGovGuarId("28,30,32,33,");
        governmentService.createGovernment(govPlainJDO);
    }

    @Test
    public void testRemoveGovernment() {
        BaseResult result = governmentService.removeGovernment(38);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testFindById() {
        GovernmentDO governmentDO = governmentService.findById(12).getData();
        System.out.println("~~~~~~~~~~~" + governmentDO.getGovId() + ", " + governmentDO.getGovName());
    }

    @Test
    public void testModifyGovernment() {
        GovPlainJDO govPlainJDO = governmentService.findGovPlainById(39).getData();
//        govPlainJDO.setGovArea("1210");
//        govPlainJDO.setGovMaxLoanAmount(new BigDecimal(1210));
//        govPlainJDO.setGovIsOfferGuar(1);
        govPlainJDO.setGovGuarId("28,30,33,");
        BaseResult result = governmentService.modifyGovernment(govPlainJDO, 27);
        System.out.println(result.getMessage());
    }

    @Test
    public void testQueryList() {
        PageResult<GovPlainJDO> page = governmentService.queryList(new GovernmentDO(), null, new PageCondition());
        System.out.println("~~~~~~~~~~~~" + page.getDataSize());
    }

    @Test
    public void testQueryListWithPlainInfo() {
        PageResult<GovPlainJDO> result = governmentService.queryListWithPlainInfo(new GovernmentDO(), null, new PageCondition());
        System.out.println(result.getDataSize());
    }

    @Test
    public void testFindPlainDOById() {
        PlainResult<GovPlainJDO> result = governmentService.findGovPlainWithAreaById(14);
        System.out.println(result.getMessage());
    }

    @Test
    public void testUpdateGovAfterReview(){
//        governmentService.updateGovAfterReview(14, ReviewState.FAILED_PASS_REVIEW);
        governmentService.updateGovAfterReview(14, ReviewState.PASS_REVIEW);
    }

    @Test
    public void testQueryReviewOpLogByEmpId(){
        ListResult<GovUpdateHistory> result = governmentService.queryReviewOpLogByEmpId(70, new PageCondition());
        System.out.println(result.getMessage());
    }

    @Test
    public void testcomputeSettGuarAmount(){
        governmentService.computeSettGuarAmount(1, new BigDecimal(200));
    }
}
