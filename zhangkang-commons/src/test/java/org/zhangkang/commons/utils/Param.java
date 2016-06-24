package org.zhangkang.commons.utils;

import org.zhangkang.commons.annotions.ParamIgnore;

/**
 * Created by root on 16-6-4.
 */
public class Param {
    private String bankCode;
    private String amount;
    @ParamIgnore
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
