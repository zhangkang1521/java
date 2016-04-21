package com.autoserve.abc.service.biz.impl.message;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.result.PageResult;

public class SysMessageInfoServiceTest extends BaseServiceTest {
	
	@Resource
	private SysMessageInfoService sysMessageInfoService;
	
	@Test
	public void testCreate()
	{
		SysMessageInfoDO sysMessageInfoDO = new SysMessageInfoDO();
		sysMessageInfoDO.setSysDelSign("0");
		sysMessageInfoDO.setSysMessageContent("这个是内容");
		sysMessageInfoDO.setSysMessageDate(new Date());
		sysMessageInfoDO.setSysMessageId(0);
		sysMessageInfoDO.setSysMessageState("");
		sysMessageInfoDO.setSysMessageTitle("这个是title");
		PageCondition pageCondition = new PageCondition();
		PageResult<SysMessageInfoDO> result = this.sysMessageInfoService.queryBytoUserId(5, pageCondition);
		System.out.println(result.getData());
		
	}
	

}
