package com.autoserve.abc.web.module.screen.infomation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class CompanyIntroduction
{
    @Autowired
    private HttpSession session;
    @Resource
	private ArticleClassService articleClassService;
    @Resource
    private ArticleInfoService articleInfoService;
    public void execute(Context context, ParameterParser params) {

    	ArticleInfo articleInfo = new ArticleInfo();
    	
    	ArticleClass title11 = articleClassService.queryById(11).getData();
    	context.put("title11",title11.getAcName());
    	articleInfo.setAiClassId(11);//公司简介
    	PageResult<ArticleInfo> companyInfo = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition());
    	if(companyInfo.isSuccess()&&companyInfo.getData().size()>0){
    	context.put("companyInfo", companyInfo.getData().get(0));//只获取第一个
    	}
    	
    	ArticleClass title12 = articleClassService.queryById(12).getData();
    	context.put("title12",title12.getAcName());
    	articleInfo.setAiClassId(12);//经营理念
    	PageResult<ArticleInfo> manageInfo = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(0,4));
    	context.put("manageInfo", manageInfo.getData());
    	
    	
    	ArticleClass title13 = articleClassService.queryById(13).getData();
    	context.put("title13",title13.getAcName());
    	articleInfo.setAiClassId(13);//公司愿景
    	PageResult<ArticleInfo> manageVision = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition());
    	if(manageVision.isSuccess()&&manageVision.getData().size()>0){
    	context.put("manageVision", manageVision.getData().get(0));//只获取第一个
    	}
    	
    	ArticleClass title14 = articleClassService.queryById(14).getData();
    	context.put("title14",title14.getAcName());
    	articleInfo.setAiClassId(14);//成功故事
    	PageResult<ArticleInfo> successInfo = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(0,10));
    	context.put("successInfo", successInfo.getData());
    	
    	
    	ArticleClass title15 = articleClassService.queryById(15).getData();
    	context.put("title15",title15.getAcName());
    	articleInfo.setAiClassId(15);//企业资质
    	PageResult<ArticleInfo> companyApt = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(0,65535));
    	context.put("companyApt", companyApt.getData());
    	
    	
    	ArticleClass title16 = articleClassService.queryById(16).getData();
    	context.put("title16",title16.getAcName());
    	articleInfo.setAiClassId(16);//办公环境
    	PageResult<ArticleInfo> officeInfo = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(0,65535));
    	context.put("officeInfo", officeInfo.getData());
    	
    	
    }
}
