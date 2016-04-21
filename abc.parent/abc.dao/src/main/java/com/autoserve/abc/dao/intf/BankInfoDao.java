/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.BankInfoDO;

/**
 * 银行信息Dao
 * 
 * @author J.YL 2014年11月13日 下午8:16:26
 */
public interface BankInfoDao extends BaseDao<BankInfoDO, Integer> {

    public List<BankInfoDO> queryAllBankInfo();

    /**
     * 根據userId查詢所有銀行資料
     */
    public List<BankInfoDO> findByUserId(Integer userId);

    /**
     * 根據userId和bank对象来查询
     */
    public List<BankInfoDO> findDataByParam(@Param("bankUserId") Integer bankUserId, @Param("bankNo") String bankNo);
    
    /**
     * 根据USERID更新银行卡信息
     */
    public int updateByUserId(BankInfoDO bankInfoDO);

}
