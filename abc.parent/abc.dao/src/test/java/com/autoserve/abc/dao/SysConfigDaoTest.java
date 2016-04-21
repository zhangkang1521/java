package com.autoserve.abc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.intf.SysConfigDao;

/**
 * @author yuqing.zheng Created on 2014-11-27,14:19
 */
public class SysConfigDaoTest extends BaseDaoTest {
    @Autowired
    private SysConfigDao sysConfigDao;

    // @Test
    public void testInsert() {
        SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfKey("CONFIG_TEST");
        sysConfigDO.setConfValue("true");
        sysConfigDO.setConfDesc("测试sys配置");

        sysConfigDao.insert(sysConfigDO);

        int configId = sysConfigDO.getConfId();
        SysConfigDO config = sysConfigDao.findById(configId);
        Assert.assertEquals(config.getConfKey(), "CONFIG_TEST");
        Assert.assertEquals(config.getConfValue(), "true");
        Assert.assertEquals(config.getConfDesc(), "测试sys配置");
        Assert.assertNotNull(config.getConfCreateTime());
        Assert.assertNotNull(config.getConfModifyTime());
    }

    // @Test
    public void testFindByConfigKey() {
        SysConfigDO config = sysConfigDao.findByConfigKey("CONFIG_TEST");
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getConfValue(), "true");
        Assert.assertEquals(config.getConfKey(), "CONFIG_TEST");
        Assert.assertEquals(config.getConfDesc(), "测试sys配置");
    }

    //@Test
    public void testUpdateByConfigKey() {
        SysConfigDO config = sysConfigDao.findByConfigKey("CONFIG_TEST");
        Assert.assertNotNull(config);

        config.setConfValue("updated value");
        sysConfigDao.updateByConfigKey(config);

        config = sysConfigDao.findByConfigKey("CONFIG_TEST");
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getConfValue(), "updated value");
    }

    // @Test
    public void testBatchUpdate() {
        List<SysConfigDO> list = new ArrayList<SysConfigDO>();
        SysConfigDO config = new SysConfigDO();
        config.setConfKey("CONFIG_TEST");
        config.setConfValue("true");
        config.setConfDesc("22222");
        SysConfigDO config2 = new SysConfigDO();
        config2.setConfKey("SMS_PASSWORD");
        config2.setConfValue("true");
        config2.setConfDesc("22222");
        list.add(config);
        list.add(config2);
        this.sysConfigDao.batchInsert(list);
    }

    @Test
    public void testSelect() {
        List<String> list = new ArrayList<String>();
        list.add("SMS_USER");
        list.add("SMS_PASSWORD");
        List<SysConfigDO> listDO = this.sysConfigDao.findListByList(list);
        System.out.println(listDO.size());
    }

    @Test
    public void testCountListWithPrefix() {
        System.out.println(sysConfigDao.countListWithPrefix("dada"));
    }

}
