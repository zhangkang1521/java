/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.dao.intf.TransferLoanDao;

/**
 * 类TransferLoanDaoTest.java的实现描述：TODO 类实现描述
 * 
 * @author segen189 2014年12月24日 上午12:11:17
 */
public class TransferLoanDaoTest extends BaseDaoTest {

    @Autowired
    private TransferLoanDao transferLoanDao;

    //@Test
    public void testSearch() {
        TransferLoanSearchDO pojo = new TransferLoanSearchDO();
        pojo.setLoanNo("宇宙第一借款");

        List<TransferLoanJDO> result = transferLoanDao.findListBySearchParam(pojo, new PageCondition());

        assert result != null;
        assert !result.isEmpty();

        System.out.println(result);
    }
}
