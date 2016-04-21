package com.autoserve.abc.service.biz.impl.article;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.enums.ColumnType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 文章栏目管理 类ArticleClassTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月28日 下午3:43:55
 */
public class ArticleClassTest extends BaseServiceTest {
    @Resource
    private ArticleClassService articleClassService;

    @Test
    public void testCreateArticleClass() {
        ArticleClass articleClass = new ArticleClass();
        articleClass.setAcName("栏目名称");
        articleClass.setAcClass(0);
        articleClass.setAcType(ColumnType.INSYSTEM);
        articleClass.setAcDesc("111");
        System.out.println(ColumnType.INSYSTEM);
        this.articleClassService.createArticleClass(articleClass);
    }

    //@Test
    public void removeArticleClass() {
        this.articleClassService.removeArticleClass(2);
    }

    //@Test
    public void testFindAndUpdate() {
        PlainResult<ArticleClass> plainResult = this.articleClassService.queryById(3);
        ArticleClass articleClass = plainResult.getData();
        articleClass.setAcName("栏目11");
        articleClass.setAcClass(0);
        this.articleClassService.modifyArticleClass(articleClass);
    }
}
