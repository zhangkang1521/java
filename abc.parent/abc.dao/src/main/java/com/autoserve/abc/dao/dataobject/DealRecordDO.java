/**
 * Copyright (C) 2006-2012 Tuniu All rigimport java.math.BigDecimal;
import java.util.Date;
7 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DealRecordDO abc_deal_record
 */
public class DealRecordDO {
    /**
     * 主键id abc_deal_record.dr_id
     */
    private Integer    drId;

    /**
     * 资金调用流水号，通过其可以确定一个唯一的资金调用,暂时未使用。在第三方支付接口不支持批量操作时使用。
     * abc_deal_record.dr_out_seq_no
     */
    private String     drOutSeqNo;

    /**
     * 内部交易流水号，一个交易流水号对应多个资金调用流水号 abc_deal_record.dr_inner_seq_no
     */
    private String     drInnerSeqNo;

    /**
     * 付款人账户id account_info表 abc_deal_record.dr_pay_account
     */
    private String     drPayAccount;

    /**
     * 收款人账户id account_info abc_deal_record.dr_receive_account
     */
    private String     drReceiveAccount;

    /**
     * 交易金钱数 abc_deal_record.dr_money_amount
     */
    private BigDecimal drMoneyAmount;

    /**
     * 交易细则类型，如果没有则为交易类型(平台手续费、平台服务费、担保费、本金、利息等) abc_deal_record.dr_detail_type
     */
    private Integer    drDetailType;

    /**
     * 交易类型(0投资，1还款，2收费，3资金划转，4退费) abc_deal_record.dr_type
     */
    private Integer    drType;

    /**
     * 交易类型对应的交易的业务类型，做外键使用 abc_deal_record.dr_business_id
     */
    private Integer    drBusinessId;

    /**
     * 资金操作记录表id abc_deal_record.dr_cash_id
     */
    private Integer    drCashId;

    /**
     * 交易状态,0 等待响应，1 成功 2 失败 abc_deal_record.dr_state
     */
    private Integer    drState;

    /**
     * 交易创建日期 abc_deal_record.dr_operate_date
     */
    private Date       drOperateDate;

    /**
     * 交易操作人 abc_deal_record.dr_operator
     */
    private Integer    drOperator;

    public Integer getDrId() {
        return drId;
    }

    public void setDrId(Integer drId) {
        this.drId = drId;
    }

    public String getDrOutSeqNo() {
        return drOutSeqNo;
    }

    public void setDrOutSeqNo(String drOutSeqNo) {
        this.drOutSeqNo = drOutSeqNo;
    }

    public String getDrInnerSeqNo() {
        return drInnerSeqNo;
    }

    public void setDrInnerSeqNo(String drInnerSeqNo) {
        this.drInnerSeqNo = drInnerSeqNo;
    }

    public String getDrPayAccount() {
        return drPayAccount;
    }

    public void setDrPayAccount(String drPayAccount) {
        this.drPayAccount = drPayAccount;
    }

    public String getDrReceiveAccount() {
        return drReceiveAccount;
    }

    public void setDrReceiveAccount(String drReceiveAccount) {
        this.drReceiveAccount = drReceiveAccount;
    }

    public BigDecimal getDrMoneyAmount() {
        return drMoneyAmount;
    }

    public void setDrMoneyAmount(BigDecimal drMoneyAmount) {
        this.drMoneyAmount = drMoneyAmount;
    }

    public Integer getDrDetailType() {
        return drDetailType;
    }

    public void setDrDetailType(Integer drDetailType) {
        this.drDetailType = drDetailType;
    }

    public Integer getDrType() {
        return drType;
    }

    public void setDrType(Integer drType) {
        this.drType = drType;
    }

    public Integer getDrBusinessId() {
        return drBusinessId;
    }

    public void setDrBusinessId(Integer drBusinessId) {
        this.drBusinessId = drBusinessId;
    }

    public Integer getDrCashId() {
        return drCashId;
    }

    public void setDrCashId(Integer drCashId) {
        this.drCashId = drCashId;
    }

    public Integer getDrState() {
        return drState;
    }

    public void setDrState(Integer drState) {
        this.drState = drState;
    }

    public Date getDrOperateDate() {
        return drOperateDate;
    }

    public void setDrOperateDate(Date drOperateDate) {
        this.drOperateDate = drOperateDate;
    }

    public Integer getDrOperator() {
        return drOperator;
    }

    public void setDrOperator(Integer drOperator) {
        this.drOperator = drOperator;
    }
}
