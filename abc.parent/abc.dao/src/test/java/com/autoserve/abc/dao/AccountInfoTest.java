/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;

/**
 * @author J.YL 2014年11月20日 下午5:21:09
 */
public class AccountInfoTest extends BaseDaoTest {

    @Resource
    private AccountInfoDao dao;

    @Test
    public void findByUserIdTest() {
        AccountInfoDO accountDo = dao.findByUserId(12345, 2);
        System.out.println(JSON.toJSON(accountDo));
    }

    @Test
    public void insertTest() {
        AccountInfoDO accountDo = new AccountInfoDO();
        accountDo.setAccountBankArea("AH");
        accountDo.setAccountBankName("CM");
        accountDo.setAccountBankBranchName("CMC");
        accountDo.setAccountLegalName("jyl");
        accountDo.setAccountMark("测试数据！");
        accountDo.setAccountNo("jiyuliang90822");
        accountDo.setAccountUserAccount("6225882502304875");
        accountDo.setAccountUserCard("1234567890");
        accountDo.setAccountUserEmail("jiyuliang90822@163.com");
        accountDo.setAccountUserId(12345);
        accountDo.setAccountUserName("jyl");
        accountDo.setAccountUserPhone("15209855821");
        accountDo.setAccountUserType(0);
        dao.insert(accountDo);
    }

    @Test
    public void updateByAccountNoTest() {
        AccountInfoDO accountDo = new AccountInfoDO();
        accountDo.setAccountBankArea("AH");
        accountDo.setAccountBankName("CM");
        accountDo.setAccountBankBranchName("CMC");
        accountDo.setAccountLegalName("jiyuliang");
        accountDo.setAccountMark("测试数据！");
        accountDo.setAccountNo("jiyuliang90822");
        accountDo.setAccountUserAccount("6225882502304875");
        accountDo.setAccountUserCard("1234567890");
        accountDo.setAccountUserEmail("jiyuliang90822@163.com");
        accountDo.setAccountUserId(12345);
        accountDo.setAccountUserName("jyl");
        accountDo.setAccountUserPhone("");
        accountDo.setAccountUserType(0);
        int flag = dao.updateByAccountNo(accountDo);
        System.out.println("adfadf " + flag);
    }
}
