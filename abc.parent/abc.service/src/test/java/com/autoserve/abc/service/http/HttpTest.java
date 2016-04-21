package com.autoserve.abc.service.http;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.result.PlainResult;

public class HttpTest extends BaseServiceTest {

	@Resource
	private AbcHttpCallService callService;

	@Test
	public void f() {
		PlainResult<String> result=callService.httpPost("http://baike.baidu.com/link?url=c_Xueq6g6BRfNnAkPGBU9y8gVrMRRbTSep4seY1kCwvqh3vuhGWXyauS17SnivWz3-kJNrX9ZzvPJ8fkrQ0s_K");
		System.out.println("参数："+result.getData());
		
	}
}
