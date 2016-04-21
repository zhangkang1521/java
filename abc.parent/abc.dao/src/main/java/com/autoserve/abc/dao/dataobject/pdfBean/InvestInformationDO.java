package com.autoserve.abc.dao.dataobject.pdfBean;

import java.math.BigDecimal;

public class InvestInformationDO {
    /**
     * 用户名
     */
    private String     userId;
    /**
     * 投资总额
     */
    private BigDecimal payTotalMoney;
    /**
     * 本息合计
     */
    private BigDecimal payCapital;
    /**
     * 期限
     */
    private String     date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getPayTotalMoney() {
        return payTotalMoney;
    }

    public void setPayTotalMoney(BigDecimal payTotalMoney) {
        this.payTotalMoney = payTotalMoney;
    }

    public BigDecimal getPayCapital() {
        return payCapital;
    }

    public void setPayCapital(BigDecimal payCapital) {
        this.payCapital = payCapital;
    }
}
