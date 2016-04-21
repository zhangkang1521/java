package org.zk.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;

/**
 * Created by zhangkang on 2016/4/15.
 */
public class SysConfigDaoTest extends BaseTest{
    @Autowired
    SysConfigDao sysConfigDao;

    @Test
    public void testCache(){
        String result = sysConfigDao.findByCodeA("bb");
        System.out.println(result);
        result = sysConfigDao.findByCodeB("bb");
        System.out.println(result);
    }

    @Test
    public void testCache2(){
        String result = sysConfigDao.findByCodeA("aa");
        System.out.println(result);
        result = sysConfigDao.findByCodeB("aa");
        System.out.println(result);
    }
}
