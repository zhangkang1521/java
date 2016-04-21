package com.autoserve.abc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.intf.AreaDao;

/**
 * @author RJQ 2014/11/17 10:07.
 */
public class AreaDaoTest extends BaseDaoTest {
    @Autowired
    private AreaDao areaDao;

    //    @Test
    //    public void areaInsertTest(){
    //        AreaDO area = new AreaDO();
    //        area.setAreaName("合肥");
    //        area.setAreaAreaCode("45423");
    //        area.setAreaSign(1);
    //        areaDao.insert(area);
    //    }
    //
    //    @Test
    //    public void areaUpdateTest(){
    //        AreaDO area = areaDao.findById(1);
    //        area.setAreaName("合肥");
    //        area.setAreaAreaCode("11111");
    //        area.setAreaSign(2);
    //        areaDao.update(area);
    //    }
    @Test
    public void areaFindAllSupperTest() {
        System.out.println(JSON.toJSON(areaDao.findAllSuperArea()));
    }

    @Test
    public void areaFindBySupperCodeTest() {
        System.out.println(JSON.toJSON(areaDao.findAreaBySuperAreaCode("130")));
    }

    @Test
    public void areaTest() {
        System.out.println(areaDao.findAreaByAreaCode("1210").getAreaSuperArea());
    }

    @Test
    public void areaTest1() {
        System.out.println(JSON.toJSON(areaDao.findAreaStringByAreaCode("5655")));
    }

    @Test
    public void testFindAreaStringsByAreaCodes() {
        List<String> paramList = new ArrayList<String>();
        paramList.add("1210");
        paramList.add("1240");
        paramList.add("1243");
        List<Map<String, String>> list = areaDao.findAreaStringsByAreaCodes(paramList);
        System.out.println(list.toString());
    }

    @Test
    public void areaListTest() {
        List<String> area = new ArrayList<String>();
        area.add("5533");
        area.add("5550");
        System.out.println(JSON.toJSON(areaDao.findAreasByAreaCodes(area)));
    }

}
