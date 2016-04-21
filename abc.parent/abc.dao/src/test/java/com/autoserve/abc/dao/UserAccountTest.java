/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.dao.intf.UserAccountDao;

/**
 * 用户资金账户测试
 * 
 * @author J.YL 2014年11月27日 下午4:01:40
 */
@Test
public class UserAccountTest extends BaseDaoTest {
    @Resource
    private UserAccountDao dao;

    public void batchSelectTest() {
        List<String> userAccounts = new ArrayList<String>();
        userAccounts.add("15209855822");
        // userAccounts.add("13260714968");
        List<Integer> ids = dao.findIdsByAccountNos(userAccounts);
        List<UserAccountDO> result = dao.findByIds(ids);
        System.out.println(JSON.toJSON(result));
    }

    @Test
    public void batchInsert() {
        List<UserAccountDO> userAccounts = new ArrayList<UserAccountDO>();
        UserAccountDO ua1 = new UserAccountDO();
        UserAccountDO ua2 = new UserAccountDO();
        ua1.setUaAccountNo("jyltest");
        ua2.setUaAccountNo("zyqtest");
        userAccounts.add(ua1);
        userAccounts.add(ua2);
        dao.batchInsert(userAccounts);
    }
}
