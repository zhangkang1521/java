package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.summary.LoanPaymentSummaryDO;
import com.autoserve.abc.dao.dataobject.summary.PaymentPlanSummaryDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;

/**
 * 类PaymentPlanTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月15日 下午2:29:46
 */
public class PaymentPlanTest extends BaseDaoTest {

    @Resource
    private PaymentPlanDao paymentPlanDao;

    //@Test
    public void testPaymentPlan() {
        List<PaymentPlanJDO> list = this.paymentPlanDao.findPlanList(null);
        System.out.println(list.size());
    }

    //@Test
    public void testFindSummaryListByIds() {
        List<LoanPaymentSummaryDO> list = paymentPlanDao.findSummaryListByIds(Arrays.asList(2, 3, 4, 5),
                Arrays.asList(2, 3, 4));
        System.out.println(list);
    }

    // @Test
    public void testBatchInsert() {
        List<PaymentPlanDO> list = new ArrayList<PaymentPlanDO>();

        list.add(createData());
        list.add(createData());
        list.add(createData());

        paymentPlanDao.batchInsert(list);

        System.out.println(list);
    }

    //@Test
    public void testInsert() {
        PaymentPlanDO paymentPlanDO = createData();
        paymentPlanDao.insert(paymentPlanDO);
        System.out.println(paymentPlanDO.getPpId() + "!!!!!");
    }

    @Test
    public void test() {
        InvestSearchJDO investSearchJDO = new InvestSearchJDO();
        investSearchJDO.setRecent(3);
        PaymentPlanSummaryDO DO = paymentPlanDao.findTotalByLoanId(2, investSearchJDO);
        System.out.println(DO);

    }

    private PaymentPlanDO createData() {
        PaymentPlanDO paymentPlanDO = new PaymentPlanDO();

        paymentPlanDO.setPpFullTransRecordId(2);
        paymentPlanDO.setPpLoanId(3);
        paymentPlanDO.setPpPayCapital(new BigDecimal(4));
        paymentPlanDO.setPpPayInterest(new BigDecimal(5));
        paymentPlanDO.setPpPayFine(new BigDecimal(6));
        paymentPlanDO.setPpPayServiceFee(new BigDecimal(7));
        paymentPlanDO.setPpPayGuarFee(new BigDecimal(8));
        paymentPlanDO.setPpPayTotalMoney(new BigDecimal(9));
        paymentPlanDO.setPpPayCollectCapital(new BigDecimal(10));
        paymentPlanDO.setPpPayCollectInterest(new BigDecimal(11));
        paymentPlanDO.setPpPayCollectFine(new BigDecimal(12));
        paymentPlanDO.setPpCollectServiceFee(new BigDecimal(13));
        paymentPlanDO.setPpCollectGuarFee(new BigDecimal(14));
        paymentPlanDO.setPpCollectTotal(new BigDecimal(15));
        paymentPlanDO.setPpRemainFine(new BigDecimal(16));
        paymentPlanDO.setPpLoanPeriod(17);
        paymentPlanDO.setPpIsClear(false);
        paymentPlanDO.setPpPaytime(new Date());
        paymentPlanDO.setPpCollecttime(new Date());
        paymentPlanDO.setPpPayState(18);
        paymentPlanDO.setPpReplaceState(false);
        paymentPlanDO.setPpLoanee(19);
        paymentPlanDO.setPpInnerSeqNo("str20");

        return paymentPlanDO;
    }

}
