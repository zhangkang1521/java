package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.UserOwnerDO;
import com.autoserve.abc.dao.intf.UserOwnerDao;

public class UserOwnerTest extends BaseDaoTest {
	@Resource
	private UserOwnerDao userOwnerDao;

	@Test
	public void test() {
		UserOwnerDO UserOwnerDO = new UserOwnerDO();

		UserOwnerDO.setUoAddress("121321");
		this.userOwnerDao.insert(UserOwnerDO);
		System.out.println(UserOwnerDO.getUoId());
	}

}
