package com.autoserve.abc.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;

public class UserDaoTest extends BaseDaoTest {
	@Autowired
	UserDao userDao;
	
	@Test
	public void test(){
//		UserDO userDO = userDao.findByName("tiuwer001");
//		UserDO userDO = userDao.findByPhone("18555680113");
		UserDO userDO = userDao.findByEmail("12345@qq.com");
		System.out.println(userDO);
	}
	
	@Test
	public void test2(){
		UserDO userDO = new UserDO();
		userDO.setUserId(131);
		userDO.setUserErrorCount(2);
		userDO.setUserErrorTime(new Date());
		userDao.updateByPrimaryKeySelective(userDO);
	}
}
