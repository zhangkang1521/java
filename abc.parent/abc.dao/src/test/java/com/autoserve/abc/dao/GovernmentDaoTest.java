package com.autoserve.abc.dao;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import com.autoserve.abc.dao.intf.GovernmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RJQ 2014/11/19 15:03.
 */
public class GovernmentDaoTest extends BaseDaoTest {
    @Autowired
    private GovernmentDao dao;


    @Test
    public void testCountListByParam() {
        int count = dao.countListByMap(new GovPlainJDO(), null, null, null, null);
        System.out.println(count);
    }

    @Test
    public void testFindListByParam() {
        GovPlainJDO govPlainJDO = new GovPlainJDO();
//        govPlainDO.setGovName("测试机构");
        PageCondition pageCondition = new PageCondition();

        List<GovPlainJDO> result = dao.findListByMap(govPlainJDO, null, null, "皇上", null, pageCondition);
//        List<GovPlainJDO> result = dao.findListByParam(govPlainJDO, pageCondition);
        System.out.println("###" + result.size());

//        List<GovernmentDO> list =  dao.findListByParam(new GovernmentDO(), new PageCondition());
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+list.size());
//        for (int i = 0, j = list.size(); i < j; ++i) {
//            System.out.println("~~~~"+list.get(i).getGovName());
//        }
    }

    @Test
    public void testFindGovPlainById() {
        GovPlainJDO govPlainJDO = dao.findGovPlainById(1);
        System.out.println(govPlainJDO);
    }

    @Test
    public void testCountForSearch() {
        int i = dao.countForSearch(new GovReviewSearchDO());
        System.out.println(i);
    }

    @Test
    public void TestSearch(){
        GovReviewSearchDO searchDO = new GovReviewSearchDO();
//        searchDO.setGovCustomerManagerName("wql");
        searchDO.setGovUserName("admin");
//        searchDO.setMaxLoanMoneyStart(new BigDecimal(200));
//        searchDO.setMaxLoanMoneyEnd(new BigDecimal(300));
//        searchDO.setGovArea("1210");
        List<GovPlainJDO> govPlainJDOs = dao.search(searchDO, new PageCondition());
        System.out.println(govPlainJDOs);

    }

    @Test
    public void testcomputeSettGuarAmount(){
//        dao.computeSettGuarAmount(1, new BigDecimal(200));
        BigDecimal bigDecimal = new BigDecimal(100);
        System.out.println(bigDecimal.negate());
    }
}
