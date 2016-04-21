package com.autoserve.abc.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.dao.intf.SysMessageReplyDao;

public class SysMessageReplyDaoTest extends BaseDaoTest{
	
	@Autowired
	private SysMessageReplyDao messageReplyDao;
	
	@Test
	public void test1(){
		SysMessageReplyDO reply = new SysMessageReplyDO();
		reply.setSysMessageId(61);
		reply.setSysReplyContent("我回复hello,world");
		reply.setSysUserId(219);
		reply.setSysUserName("atxw219");
		reply.setSysReplyDate(new Date());
		messageReplyDao.insert(reply);
	}
}
