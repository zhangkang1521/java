package com.autoserve.abc.service.biz.impl.article;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.enums.BooleanType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 文章管理测试 类ArticleInfoTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月29日 下午2:56:13
 */
public class ArticleInfoTest extends BaseServiceTest {
    @Resource
    private ArticleInfoService articleInfoService;

    //@Test
    public void testCreateArticleInfo() {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setAiClassId(2);
        articleInfo.setAiArticleTitle("str3");
        articleInfo.setAiArticlecontent("str4");
        articleInfo.setAiIsTop(BooleanType.no);
        articleInfo.setAiArticleSource("str6");
        articleInfo.setAiAddEmp(7);
        articleInfo.setAiAddDate(new Date());
        articleInfo.setAiArticleCount(8);
        this.articleInfoService.createArticleInfo(articleInfo);
    }

    //@Test
    public void testRemoveArticleInfo() {
        this.articleInfoService.removeArticleInfo(2);
    }

    //@Test
    public void testModifyArticleClass() {
        PlainResult<ArticleInfo> result = this.articleInfoService.queryById(3);
        ArticleInfo articleInfo = result.getData();
        articleInfo.setAiClassId(5555);
        articleInfo.setAiArticleTitle("str3");
        articleInfo.setAiArticlecontent("str4");
        articleInfo.setAiIsTop(BooleanType.yes);
        articleInfo.setAiArticleSource("str6");
        articleInfo.setAiAddEmp(7);
        articleInfo.setAiAddDate(new Date());
        articleInfo.setAiArticleCount(8);
        this.articleInfoService.modifyArticleInfo(articleInfo);
    }

    @Test
    public void testQueryArticleInfoListByParam() {
        PageCondition pageCondition = new PageCondition();
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setAiClassId(2);
        PageResult<ArticleInfo> pageResult = this.articleInfoService.queryArticleInfoListByParam(articleInfo,
                pageCondition);
        System.out.println(pageResult.getDataSize());
    }
}
