package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.dao.intf.ButtonDao;
import org.testng.annotations.Test;

import javax.annotation.Resource;

public class ButtonDaoTest extends BaseDaoTest{

	@Resource
	private ButtonDao buttonDao;
	
	@Test
	public void  testInsert(){
		ButtonDO  btn=new ButtonDO();
		btn.setBtnTag("deltest");
		btn.setBtnSort(1);
		btn.setBtnName("测试");
		buttonDao.insert(btn);
		btn.setBtnDes("aaaaaaaaaaaaa");
		buttonDao.update(btn);
	}
}
