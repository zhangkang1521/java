package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.dao.intf.MenuDao;

public class MenuDaoTest extends BaseDaoTest {

    @Resource
    private MenuDao menuDao;

    // @Test
    public void createData() {
        MenuDO menu = new MenuDO();
        menu.setMenuName("菜单1");
        menu.setMenuUrl("#");
        menu.setMenuParent(4);
        menu.setMenuSort(1);
        menu.setMenuVisible(5);
        int i = menuDao.insert(menu);
        System.out.println(i);

    }

    @Test
    public void queryByIds() {
        System.err.println(JSON.toJSONString(menuDao.findByEmpId(27)));
    }
}
