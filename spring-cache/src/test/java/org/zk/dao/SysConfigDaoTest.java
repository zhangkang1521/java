package org.zk.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangkang on 2016/4/15.
 */
public class SysConfigDaoTest extends BaseTest{
    @Autowired
    SysConfigDao sysConfigDao;

    @Test
    public void testFindUser(){
        sysConfigDao.findUser();
    }

    @Test
    public void testExpire(){
        sysConfigDao.testExpire();
    }

    @Test
    public void testFindParam(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId", 400);
        map.put("status", "active");
        sysConfigDao.findByParam(1, 10, map);
    }

    @Test
    public void testCache(){
        String result = sysConfigDao.findByCodeA("aa");
        System.out.println(result);
//        result = sysConfigDao.findByCodeB("bb");
//        System.out.println(result);
    }

    @Test
    public void testCacheEvicct(){
        sysConfigDao.addA("aa");
    }

    @Test
    public void testCache3(){
        String result = sysConfigDao.find2();
        System.out.println(result);
        result = sysConfigDao.find();
        System.out.println(result);
    }

    @Test
    public void testCache4(){
        String result = sysConfigDao.find2();
        System.out.println(result);
    }
}
