/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.intf.BankInfoDao;
import com.autoserve.abc.service.biz.convert.BankInfoConverter;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 银行信息
 * 
 * @see 暂时未用
 * @author J.YL 2014年11月17日 下午4:02:43
 */
@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Resource
    private BankInfoDao bankDao;

    @Override
    public BaseResult createBankInfo(BankInfo bankInfo) {
        this.bankDao.insert(BankInfoConverter.toBankInfoDO(bankInfo));
        return new BaseResult();
    }

    @Override
    public BaseResult removeBankInfo(String userID, String bankNo) {
        return null;
    }

    @Override
    public BaseResult modifyBankInfo(String userID, String bankNo) {
        return null;
    }

    @Override
    public ListResult<BankInfoDO> queryListBankInfo(Integer userID) {

        ListResult<BankInfoDO> result = new ListResult<BankInfoDO>();

        List<BankInfoDO> list = this.bankDao.findByUserId(userID);

        result.setData(list);

        return result;
    }

    @Override
    public PlainResult<BankInfoDO> queryListBankInfoById(Integer id) {

        PlainResult<BankInfoDO> result = new PlainResult<BankInfoDO>();

        BankInfoDO list = this.bankDao.findById(id);

        result.setData(list);

        return result;
    }

    @Override
    public BaseResult modifyBankInfo(BankInfo pojo) {
        this.bankDao.update(BankInfoConverter.toBankInfoDO(pojo));
        return new BaseResult();
    }

    @Override
    public PlainResult<BankInfoDO> queryBankInfo(String userID, String bankNo) {
        Integer bankUserId = Integer.valueOf(userID);
        PlainResult<BankInfoDO> result = new PlainResult<BankInfoDO>();
        List<BankInfoDO> list = this.bankDao.findDataByParam(bankUserId, bankNo);
        if (list.size() == 0) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage("没有该银行卡记录");
        } else {
            result.setData(list.get(0));
        }
        return result;
    }

	@Override
	public BaseResult modifyBankInfoByUserId(BankInfo bankInfo) {
		 BaseResult result = new BaseResult(); 
		 int cum = bankDao.updateByUserId(BankInfoConverter.toBankInfoDO(bankInfo));
		 if(cum < 1){
			 result.setMessage("更新用户银行卡信息失败!");
			 result.setSuccess(false);
			 return result;
		 }
		result.setSuccess(true);
		return result;
	}

}
