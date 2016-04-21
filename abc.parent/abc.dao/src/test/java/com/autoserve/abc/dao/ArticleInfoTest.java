package com.autoserve.abc.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.dao.intf.ArticleInfoDao;

/**
 * 文章管理测试 类ArticleInfoTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月29日 上午10:55:55
 */
public class ArticleInfoTest extends BaseDaoTest {

    @Resource
    private ArticleInfoDao articleInfoDao;

    //@Test
    public void testInsert() {
        ArticleInfoDO articleInfoDO = new ArticleInfoDO();
        articleInfoDO.setAiClassId(2);
        articleInfoDO.setAiArticleTitle("str3");
        articleInfoDO.setAiArticlecontent("str4");
        articleInfoDO.setAiIsTop(1);
        articleInfoDO.setAiArticleSource("str6");
        articleInfoDO.setAiAddEmp(7);
        articleInfoDO.setAiAddDate(new Date());
        articleInfoDO.setAiArticleCount(8);
        this.articleInfoDao.insert(articleInfoDO);
    }

    //@Test
    public void testDelete() {
        this.articleInfoDao.delete(1);
    }

    //@Test
    public void testUpdate() {
        ArticleInfoDO articleInfoDO = this.articleInfoDao.findById(2);
        articleInfoDO.setAiClassId(2);
        articleInfoDO.setAiArticleTitle("222");
        articleInfoDO.setAiArticlecontent("str4");
        articleInfoDO.setAiIsTop(1);
        articleInfoDO.setAiArticleSource("str6");
        articleInfoDO.setAiAddEmp(7);
        articleInfoDO.setAiAddDate(new Date());
        articleInfoDO.setAiArticleCount(8);
        this.articleInfoDao.update(articleInfoDO);
    }

    @Test
    public void testFindListByParam() {
        ArticleInfoDO articleInfo = new ArticleInfoDO();
        articleInfo.setAiClassId(2);
        int count = this.articleInfoDao.countListByParam(articleInfo);
        System.out.println(count);
    }
}
