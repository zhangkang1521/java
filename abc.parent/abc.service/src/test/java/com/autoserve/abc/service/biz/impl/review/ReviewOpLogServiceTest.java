package com.autoserve.abc.service.biz.impl.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.result.ListResult;

public class ReviewOpLogServiceTest extends BaseServiceTest{
	
	@Autowired
	ReviewOpLogService reviewlogService;
	
	@Test
	public void test1(){
		ListResult<ReviewOp> result = reviewlogService.queryReviewOpHistory(385);
		System.out.println(result.getData().size());
	}
}
