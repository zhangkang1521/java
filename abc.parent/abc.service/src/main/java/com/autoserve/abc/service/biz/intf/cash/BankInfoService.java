/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 银行信息service
 * 
 * @author J.YL 2014年11月17日 下午4:02:26
 */
public interface BankInfoService {

    /**
     * 创建用户银行账户
     * 
     * @param bankInfo
     * @return BaseResult
     */
    public BaseResult createBankInfo(BankInfo bankInfo);

    /**
     * 根据用户ID&银行卡号删除银行卡
     * 未实现
     * @param userID
     * @param bankNo
     * @return
     */
    public BaseResult removeBankInfo(String userID, String bankNo);
	/**
	 * 未实现
	 * @param userID
	 * @param bankNo
	 * @return
	 */
    public BaseResult modifyBankInfo(String userID, String bankNo);

    public ListResult<BankInfoDO> queryListBankInfo(Integer userID);

    /**
     * 根据ID查询银行
     * @param id
     * @return
     */
	public PlainResult<BankInfoDO> queryListBankInfoById(Integer id);
	
	 /**
     * 修改用户银行账户
     * 
     * @param bankInfo
     * @return BaseResult
     */
    public BaseResult modifyBankInfo(BankInfo bankInfo);

	/**
	 * 根据用户id 和银行卡号查询银行卡
	 * @param userID
	 * @param bankNo
	 * @return
	 */
    public PlainResult<BankInfoDO> queryBankInfo(String userID, String bankNo);
    
    /**
     * 根据用户USERID修改
     * 
     * @param bankInfo
     * @return BaseResult
     */
    public BaseResult modifyBankInfoByUserId(BankInfo bankInfo);

}
