package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.ArticleClassDO;
import com.autoserve.abc.dao.intf.ArticleClassDao;

/**
 * 类articleClass.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月28日 下午2:08:50
 */
public class ArticleClassTest extends BaseDaoTest {

    @Resource
    private ArticleClassDao articleClassDao;

    @Test
    public void testInset() {
        ArticleClassDO articleClass = new ArticleClassDO();
        articleClass.setAcName("栏目名称");
        articleClass.setAcClass(0);
        this.articleClassDao.insert(articleClass);
    }

    // @Test
    public void testDelete() {
        this.articleClassDao.delete(1);
    }

    //@Test
    public void testUpdate() {
        ArticleClassDO articleClass = this.articleClassDao.findById(2);
        articleClass.setAcName("栏目名称修改");
        articleClass.setAcClass(0);
        this.articleClassDao.update(articleClass);
    }

    // @Test
    public void testFindById() {
        ArticleClassDO articleClass = this.articleClassDao.findById(2);

        System.out.println(articleClass.getAcName());
    }
}
