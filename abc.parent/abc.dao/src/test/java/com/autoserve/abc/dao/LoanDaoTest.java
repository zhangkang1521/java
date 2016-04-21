/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanCarDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.intf.LoanCarDao;
import com.autoserve.abc.dao.intf.LoanDao;

/**
 * 类LoanDaoTest.java的实现描述：TODO 类实现描述
 * 
 * @author segen189 2014年11月18日 上午10:45:32
 */
public class LoanDaoTest extends BaseDaoTest {

    @Resource
    private LoanDao    loanDao;
    @Resource
    private LoanCarDao LoanCarDao;

    //@Test
    public void testInsert() {
        LoanDO loanDO = new LoanDO();
        loanDO.setLoanId(1);
        loanDO.setLoanLogo("str2");
        loanDO.setLoanIntentId(3);
        loanDO.setLoanNo("str4");
        loanDO.setLoanEmpType(5);
        loanDO.setLoanUserId(6);
        loanDO.setLoanGov(7);
        loanDO.setLoanGuarGov(8);
        loanDO.setLoanMoney(new BigDecimal(9));
        loanDO.setLoanRate(new BigDecimal(10));
        loanDO.setLoanPeriod(11);
        loanDO.setLoanPeriodType(12);
        loanDO.setLoanMinInvest(new BigDecimal(13));
        loanDO.setLoanMaxInvest(new BigDecimal(14));
        loanDO.setLoanCurrentInvest(new BigDecimal(15));
        loanDO.setLoanPayType(16);
        loanDO.setLoanInvestStarttime(new Date());
        loanDO.setLoanInvestEndtime(new Date());
        loanDO.setLoanInvestFulltime(new Date());
        loanDO.setLoanClearType(18);
        loanDO.setLoanUse("str19");
        loanDO.setLoanState(20);
        loanDO.setLoanCategory(21);
        loanDO.setLoanCategoryId(22);
        loanDO.setLoanFileUrl("str23");
        loanDO.setLoanCreator(24);
        loanDO.setLoanModifier(25);
        loanDO.setLoanCreatetime(new Date());
        loanDO.setLoanModifiytime(new Date());
        loanDO.setLoanDeleted(26);
        loanDO.setLoanNote("str27");
        loanDO.setInvestRedsendRatio(0.56);
        loanDO.setInvestReduseRatio(0.56);
        loanDO.setLoanCurrentValidInvest(new BigDecimal(0));

        int a = loanDao.insert(loanDO);
        assert loanDO.getLoanId() != null;
        System.out.println(a);
    }

    //@Test
    public void testFindPageList() {
        LoanDO loanDO = new LoanDO();

        loanDO.setLoanLogo("str2");
        loanDO.setLoanIntentId(3);

        PageCondition pageCondition = new PageCondition();
        List<LoanDO> result = loanDao.findListByParam(loanDO, pageCondition);
        System.out.println("###" + result.size());
    }

    //@Test
    public void testFindById() {
        LoanDO loan = loanDao.findByIntentApplyId(3);
        System.out.println(loan.getLoanLogo() + "  !!!!!!!!!!");
    }

    //@Test
    public void testFindByList() {
        List<LoanDO> result = loanDao.findByList(Arrays.asList(321, 223));
        System.out.println(result);
    }

    //@Test
    public void testUpdate() {
        LoanDO loan = loanDao.findById(11);
        loan.setLoanLogo("logo");

        loanDao.update(loan);
        loan = loanDao.findById(11);
        System.out.println(loan.getLoanLogo() + "   !!!!!!!!!!!!");
    }

    //@Test
    public void testFindByParam() {
        LoanDO loan = new LoanDO();

        loan.setLoanLogo("str2");
        loan = loanDao.findByParam(loan);
        System.out.println(loan.getLoanId() + "  !!!!!!!!");
    }

    // @Test
    public void testfindListByParam() {
        LoanDO loan = new LoanDO();
        PageCondition pageCondition = new PageCondition();
        loan.setLoanLogo("str2");
        List<LoanDO> list = loanDao.findListByParam(loan, pageCondition);
        System.out.println(list.size() + "  !!!!!!!!");
    }

    //@Test
    public void testCountListByParam() {
        InvestSearchJDO investSearchJDO = new InvestSearchJDO();
        List<Integer> list = new ArrayList<Integer>();
        list.add(77);
        list.add(78);
        list.add(79);
        investSearchJDO.setLoanName("78");
        System.out.println(this.loanDao.findByListAndSrearch(list, investSearchJDO).size());
    }

    //@Test
    public void testfindListBySearchParam() {
        LoanSearchDO pojo = new LoanSearchDO();
        PageCondition pageCondition = new PageCondition();
        pojo.setIsFromIntent(true);
        pojo.setLoaneeName("季胖胖");
        List<LoanDO> list = loanDao.findListBySearchParam(pojo, pageCondition);
        System.out.println(list + "  !!!!!!!!");
    }

   // @Test
    public void testLoanCarDao() {
        List<LoanCarDO> list = new ArrayList<LoanCarDO>();
        LoanCarDO loanCarDO = new LoanCarDO();
        loanCarDO.setLcAssessMoney(BigDecimal.ZERO);
        loanCarDO.setLcLoanId(1);
        loanCarDO.setLcBuyYear(1);
        list.add(loanCarDO);
        this.LoanCarDao.batchInsert(list);

    }
    @Test
    public void findListBySearch(){
    	LoanSearchDO searchDo=new LoanSearchDO();
    	 List<Integer> stateList = new ArrayList<Integer>();
         stateList.add(9);
         searchDo.setLoanState(stateList);
         //开始投标时间
         searchDo.setLoanInvestStarttime(new Date());
         //开始结束时间
         searchDo.setLoanInvestEndtime(new Date());
    	 this.loanDao.findListBySearch(searchDo);
    }

}
