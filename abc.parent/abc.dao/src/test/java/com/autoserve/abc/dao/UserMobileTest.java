package com.autoserve.abc.dao;

import javax.annotation.Resource;

import com.autoserve.abc.dao.intf.CompanyCustomerDao;

/**
 * 类UserMobileTest.java的实现描述：测试类
 * 
 * @author lipeng 2014年12月23日 下午3:03:46
 */
public class UserMobileTest extends BaseDaoTest {
    @Resource
    private CompanyCustomerDao companyCustomerDao;

    //   @Test
    //    public void test(){
    //        UserDO userDO = new UserDO();
    //        CompanyCustomerWithBLOBsDO  userDOs = companyCustomerDao.findById(1);
    //        System.out.println(userDOs.toString());
    //    }

    //@Test
    //    public void testquseryUser(){
    //    	UserDO userDO=new UserDO();
    //    	userDO.setUserId(114);    	
    //    	userDO.setUserDealPwd("96e79218965eb72c92a549dd5a330112");
    //    	userDao.findListByParam(userDO, new PageCondition());
    //    	userDao.countListByParam(userDO);
    //    
    //    }
}
