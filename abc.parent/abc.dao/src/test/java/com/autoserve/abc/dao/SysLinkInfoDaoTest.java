package com.autoserve.abc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.dao.intf.SysLinkInfoDao;

public class SysLinkInfoDaoTest extends BaseDaoTest {

    @Resource
    private SysLinkInfoDao sysLinkInfoDao;

    //@Test
    public void testInsert() {
        SysLinkInfoDO linkInfo = new SysLinkInfoDO();

        linkInfo.setSlAddress("address");
        linkInfo.setSlMark("str22");
        sysLinkInfoDao.insert(linkInfo);
    }

    //@Test
    public void testfindById() {
        SysLinkInfoDO linkInfo = sysLinkInfoDao.findById(1);
        System.out.println(linkInfo.getSlMark() + "   !!!!!!!!!!!!!");
    }

    //@Test
    public void testDelete() {
        sysLinkInfoDao.delete(1);
    }

    //@Test
    public void testUpdate() {
        SysLinkInfoDO linkInfo = sysLinkInfoDao.findById(2);
        linkInfo.setSlAddress("2222");
        linkInfo.setSlMark("3333");
        sysLinkInfoDao.update(linkInfo);
    }

    @Test
    public void testFindListByParam() {
        List<SysLinkInfoDO> result = sysLinkInfoDao.findListByParam(null, null);
        System.out.println(result.size());
    }
}
