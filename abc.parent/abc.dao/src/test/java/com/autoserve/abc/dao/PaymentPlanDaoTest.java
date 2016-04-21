package com.autoserve.abc.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.google.common.collect.Lists;


public class PaymentPlanDaoTest extends BaseDaoTest {
	
	@Resource
	PaymentPlanDao pDao;
	
	@Test
	public void test1(){
		PageCondition pageCondition = new PageCondition(1, 5);
		PaymentPlanSearchDO pp = new PaymentPlanSearchDO();
		List<Integer> payStates = Lists.newArrayList(0, 1, 2);
		pp.setPayStates(payStates);
		pp.setLoanNO("auto");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2016, 1, 1, 0, 0, 0);
		Date payTime1 = cal1.getTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(payTime1));
		pp.setPayTime1(payTime1);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2016, 5, 1, 23, 59, 59);
		Date payTime2 = cal2.getTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(payTime2));
		pp.setPayTime2(payTime2);
		
		pp.setPayType(1);
		List list = pDao.findPlanList2(pp, pageCondition);
		System.out.println(list.size());
	}
}
