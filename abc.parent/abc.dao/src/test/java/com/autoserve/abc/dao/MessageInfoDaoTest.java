package com.autoserve.abc.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.intf.MessageInfoDao;

public class MessageInfoDaoTest extends BaseDaoTest{
	@Resource
	MessageInfoDao msgInfoDao;
	
	@Test
	public void testInsert(){
		SysMessageInfoDO message = new SysMessageInfoDO();
		message.setSysMessageTitle("平淡");
		message.setSysMessageContent("平淡平淡平淡平淡");
		//
		message.setSysUserId(1);
		message.setSysUserName("admin");
//		message.setSysUserType("");
		
		message.setSysToUser(219);
		message.setSysToUserName("atxw202");
//		message.setSysToUserType("");
		
		message.setSysMessageState("0");
		message.setSysDelSign("0");
		message.setSysMessageDate(new Date());
		message.setSysRead("0");
		
		msgInfoDao.insert(message);
		
	}
	
	@Test
	public void test1(){
		SysMessageInfoDO message = new SysMessageInfoDO();
		message.setSysMessageTitle("hello");
		message.setSysToUserName("at");
		msgInfoDao.countMessageByDo(message, new PageCondition(1, 10));
		msgInfoDao.queryMessageByDo(message, new PageCondition(1, 10));
	}
	
}
