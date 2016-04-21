
package com.autoserve.abc.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.search.FundApplySearchDO;
import com.autoserve.abc.dao.intf.FundApplyDao;

/**
 * 类FundApplyDaoTest.java的实现描述：TODO 类实现描述
 *
 * @author wangyongheng 2014/12/03
 */
public class FundApplyDaoTest extends BaseDaoTest {

    @Resource
    private FundApplyDao fundApplyDao;

    @Test
    public void testInsert() {
    	FundApplyDO fundApplyDO = new FundApplyDO();
        fundApplyDO.setFaFundId(1);
        fundApplyDO.setFaFundNo("str2");
        fundApplyDO.setFaFundName("str3");
        fundApplyDO.setFaFundComp("str4");
        fundApplyDO.setFaFundMoney(new BigDecimal(5));
        fundApplyDO.setFaFundPeriod(new BigDecimal(6));
        fundApplyDO.setFaFundRate(new BigDecimal(7));
        fundApplyDO.setFaMinMoney(new BigDecimal(8));
        fundApplyDO.setFaFundIndustry("str9");
        fundApplyDO.setFaPayType(1);
        fundApplyDO.setFaFundType("str10");
        fundApplyDO.setFaMartgageRate(new BigDecimal(11));
        fundApplyDO.setFaMartgageIntrol("str12");
        fundApplyDO.setFaFundIntrol("str13");
        fundApplyDO.setFaFundUse("str14");
        fundApplyDO.setFaFundRisk("str15");
        fundApplyDO.setFaPayResource("str16");
        fundApplyDO.setFaProjectIntrol("str17");
        fundApplyDO.setFaCompIntrol("str18");
        fundApplyDO.setFaFundState(1);
        fundApplyDO.setFaAddDate(new Date());

        fundApplyDao.insert(fundApplyDO);
        assert fundApplyDO.getFaFundId() != null;
        System.out.println("!!!!!" + fundApplyDO.getFaFundId());
    }

    @Test
    public void testDelete() {
    	fundApplyDao.delete(4);
    }

    @Test
    public void testFindById() {
    	FundApplyDO fundApplyDO = new FundApplyDO();
    	fundApplyDO = fundApplyDao.findById(8);
    	System.out.println(fundApplyDO.getFaFundNo()+"^^^^^^^^^^^^^^^^^^^^");
    	
    }

    @Test
    public void testUpdate() {
    	FundApplyDO fundApplyDO = new FundApplyDO();
    	fundApplyDO.setFaFundId(11);
    	fundApplyDO.setFaFundNo("str2");
        fundApplyDO.setFaFundName("str3");
        fundApplyDO.setFaFundComp("str4");
        fundApplyDO.setFaFundMoney(new BigDecimal(5));
        fundApplyDO.setFaFundPeriod(new BigDecimal(6));
        fundApplyDO.setFaFundRate(new BigDecimal(7));
        fundApplyDO.setFaMinMoney(new BigDecimal(8));
        fundApplyDO.setFaFundIndustry("industry");
        fundApplyDO.setFaPayType(1);
        fundApplyDO.setFaFundType("eeeeeeeeee");
        fundApplyDO.setFaMartgageRate(new BigDecimal(11));
        fundApplyDO.setFaMartgageIntrol("DDDDDDD");
        fundApplyDO.setFaFundIntrol("RRRRRRRRRRRRR");
        fundApplyDO.setFaFundUse("uuuuuuuu");
        fundApplyDO.setFaFundRisk("fffffffff");
        fundApplyDO.setFaPayResource("gggggggg");
        fundApplyDO.setFaProjectIntrol("str17");
        fundApplyDO.setFaCompIntrol("str18");
        fundApplyDO.setFaFundState(1);
        fundApplyDO.setFaAddDate(new Date());
    	fundApplyDao.update(fundApplyDO);
        System.out.println(fundApplyDO.getFaFundIndustry() + "  !!!!!!!!");
    }

    @Test
    public void testFindByParam(){
    	PageCondition pageCondition = new PageCondition();
    	FundApplyDO fundApplyDO = new FundApplyDO();
    	//fundApplyDO.setFaFundId(8);
    	List<FundApplyDO> folist= fundApplyDao.findListByParam(fundApplyDO, pageCondition);
    	for (FundApplyDO fundApplyDO2 : folist) {
			System.out.println("^^^^^^^^"+fundApplyDO2.getFaFundName());
		}
    }

    @Test
    public void testCountListByParam(){
    	FundApplyDO fundApplyDO = new FundApplyDO();
    	//fundApplyDO.setFaFundId(8);
    	int count= fundApplyDao.countListByParam(fundApplyDO);
    	System.out.println(count);
    }
    
    @Test
    public void testFindPageListBySearchParam(){
    	PageCondition pageCondition = new PageCondition();
    	FundApplySearchDO fundApplySearchDO = new FundApplySearchDO();
    	fundApplySearchDO.setFaFundMoneyFrom(BigDecimal.ONE);
    	fundApplySearchDO.setFaFundMoneyTo(new BigDecimal(1000));
    	//fundApplySearchDO.setFaAddDateFrom(DateUtils);
    	fundApplySearchDO.setFaAddDateTo(new Date());
    	List<FundApplyDO> folist= fundApplyDao.findPageListBySearchParam(fundApplySearchDO, pageCondition);
    	for (FundApplyDO fundApplyDO2 : folist) {
			System.out.println("^^^^^^^^"+fundApplyDO2.getFaFundName());
		}
    }
    
    @Test
    public void testCountListBySearchParam(){
    	FundApplySearchDO fundApplySearchDO = new FundApplySearchDO();
    	fundApplySearchDO.setFaFundMoneyFrom(BigDecimal.ONE);
    	fundApplySearchDO.setFaFundMoneyTo(new BigDecimal(1000));
    	//fundApplySearchDO.setFaAddDateFrom(new Date());
    	fundApplySearchDO.setFaAddDateTo(new Date());
    	int count = fundApplyDao.countListBySearchParam(fundApplySearchDO);
    	System.out.println("^^^^^^^^^^"+count);
    	
    }
    
}
