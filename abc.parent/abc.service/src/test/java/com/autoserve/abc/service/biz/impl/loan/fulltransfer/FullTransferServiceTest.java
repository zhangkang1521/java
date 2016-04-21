package com.autoserve.abc.service.biz.impl.loan.fulltransfer;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.citrus.util.Assert;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * @author yuqing.zheng Created on 2014-12-29,14:45
 */
public class FullTransferServiceTest extends BaseServiceTest {
    @Autowired
    private FullTransferService fullTransferService;

    @Autowired
    private LoanDao             loanDao;

    @Resource
    private InvestOrderService  investOrderService;

    @Test
    public void testCommonBidMoneyTransfer() {

        LoanDO loanDO = new LoanDO();
        loanDO.setLoanId(57);
        loanDO.setLoanState(11);
        loanDO.setLoanMoney(new BigDecimal(200000));
        loanDO.setLoanPeriod(15);
        loanDO.setLoanPeriodType(2);//按月
        loanDO.setLoanRate(new BigDecimal(12));

        //        loanDO.setLoanPayType(1);// 等额本息
        loanDO.setLoanPayType(2);// 按月还息到期还本
        //        loanDO.setLoanClearType(1); // 固定还款日
        loanDO.setLoanClearType(2); // 非固定还款日
        //   loanDao.update(loanDO);

        /*
         * String requestList = "LM12346][LM32][1465"; String requestArray[]
         * =requestList.split("\\]\\["); String LoanNoList=requestArray[0];
         * String LoanJsonList=requestArray[1]; for(int
         * i=0;i<requestArray.length;i++){ requestList
         * =requestList.replace("][",""); }
         */

        BaseResult result = fullTransferService.commonBidMoneyTransfer(64, 11.0, 1,
                FullTransferType.COMMON_LOAN_FULL_TRANSFER, 113);
        // 不代表满标资金换转成功，只有callback成功，才算最终成功
        System.out.println(result.getMessage());
        Assert.assertTrue(result.isSuccess());

    }

}
