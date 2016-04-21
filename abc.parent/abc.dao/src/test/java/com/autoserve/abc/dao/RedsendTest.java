package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.dao.intf.RedsendDao;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class RedsendTest extends BaseDaoTest {

    @Resource
    private RedsendDao redsendDao;

    //@Test
    public void insertTest() {
        RedsendDO redsendDO = new RedsendDO();
        redsendDO.setRsRedId(2);
        redsendDO.setRsAmt(23213.5);
        redsendDO.setRsTheme("红包发放");
        redsendDO.setRsValidAmount(50.0);
        redsendDO.setRsType(1);
        redsendDO.setRsUserid(6);
        redsendDO.setRsBizid(7);
        redsendDO.setRsUseScope("str8");
        redsendDO.setRsLifetime(9);
        redsendDO.setRsInvestAmount(20.0);
        redsendDO.setRsStarttime(new Date());
        redsendDO.setRsClosetime(new Date());
        redsendDO.setRsSendtime(new Date());
        redsendDO.setRsSender(11);
        redsendDO.setRsState(1);
        redsendDao.insert(redsendDO);
        System.out.println("=================================" + redsendDO.getRsId());
    }

    //@Test
    public void findByIdTest() {
        RedsendDO redsendDO = redsendDao.findById(1);
        System.out.println("=================================" + redsendDO.getRsId());
    }

    //@Test
    public void updateTest() {
        RedsendDO redsendDO = new RedsendDO();
        redsendDO.setRsId(1);
        redsendDO.setRsTheme("红包发放修改");
        redsendDao.update(redsendDO);
        System.out.println("=================================" + redsendDO.getRsId());
    }

    // @Test
    public void findByParamTest() {
        RedsendDO redsendDO = new RedsendDO();
        List<RedsendDO> redList = redsendDao.findListByParam(redsendDO, null);
        System.out.println("======================================================" + redList.size());

    }

    //@Test
    /*
     * public void findByJParemTest(){ RedsendJDO redsendJDO = new RedsendJDO();
     * List<RedsendJDO> redList = redsendDao.findListByJParam(redsendJDO, null);
     * System
     * .out.println("======================================================"
     * +redList.size()); }
     */
    /**
     * 测试countListByParam
     * 
     * @author fangrui
     */
    //@Test
    public void testFindList() {

        List<RedsendJDO> list = redsendDao.findInviteList(null, null);
        System.out.println("~~~~~~~~~~~~~" + list.size());

    }

    @Test
    public void testCount() {
        int num = redsendDao.countInviteList(null);
        System.out.println("~~~~~~~~~~~~~" + num);

    }
}
