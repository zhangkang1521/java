package com.autoserve.abc.web.module.screen.account.myLoan;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentPlanVO
{
	 /**
     * 主键id
     */
	private Integer      id;
	
	/**
     * 应还本金
     */
    private BigDecimal   payCapital;

    /**
     * 应还利息
     */
    private BigDecimal   payInterest;
    /**
     * 应还本息和
     */
    private BigDecimal   payCapIn;
    /**
     * 实还本金
     */
    private BigDecimal   payCollectCapital;

    /**
     * 实还利息
     */
    private BigDecimal   payCollectInterest;
    /**
     * 实还本息和
     */
    private BigDecimal	payCollectCaIn;
    /**
     * 是否还清1 已还清 0 未还清
     */
    private Integer isClear;
    /**
     * 应还日期
     */
    private String payTime;
    /**
     * 当前期数
     */
    private Integer  loanPeriod;
    /**
     * 还款项目名称
     */
    private String loanNo;
    /**
     * 还款的项目id
     */
    private Integer loanId;
    /**
     * 应还罚金
     */
    private BigDecimal payFine;
    /**
     * 实还罚金
     */
    private BigDecimal payCollectFine;
    /**
     * 实还平台服务费
     */
    private BigDecimal collectServiceFee;
    /**
     * 应还平台服务费
     */
    private BigDecimal payServiceFee;
	public Integer getLoanId()
	{
		return loanId;
	}
	public void setLoanId(Integer loanId)
	{
		this.loanId = loanId;
	}
	public String getLoanNo()
	{
		return loanNo;
	}
	public void setLoanNo(String loanNo)
	{
		this.loanNo = loanNo;
	}
	public Integer getLoanPeriod()
	{
		return loanPeriod;
	}
	public void setLoanPeriod(Integer loanPeriod)
	{
		this.loanPeriod = loanPeriod;
	}
	public String getPayTime()
	{
		
		return payTime;
	}
	public void setPayTime(Date payTime)
	{
		this.payTime = new SimpleDateFormat("yyyy-MM-dd").format(payTime);
	}
	public Integer getIsClear()
	{
		return isClear;
	}
	public void setIsClear(Integer isClear)
	{
		this.isClear = isClear;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public BigDecimal getPayCapital()
	{
		return payCapital;
	}
	public void setPayCapital(BigDecimal payCapital)
	{
		this.payCapital = payCapital;
	}
	public BigDecimal getPayInterest()
	{
		return payInterest;
	}
	public void setPayInterest(BigDecimal payInterest)
	{
		this.payInterest = payInterest;
	}
	public BigDecimal getPayCapIn()
	{
		return this.payInterest.add(this.payCapital);
	}
	public void setPayCapIn(BigDecimal payCapIn)
	{
		this.payCapIn = payCapIn;
	}
	public BigDecimal getPayCollectCapital()
	{
		return payCollectCapital;
	}
	public void setPayCollectCapital(BigDecimal payCollectCapital)
	{
		this.payCollectCapital = payCollectCapital;
	}
	public BigDecimal getPayCollectInterest()
	{
		return payCollectInterest;
	}
	public void setPayCollectInterest(BigDecimal payCollectInterest)
	{
		this.payCollectInterest = payCollectInterest;
	}
	public BigDecimal getPayCollectCaIn()
	{
		return payCollectInterest.add(payCollectCapital);
	}
	public void setPayCollectCaIn(BigDecimal payCollectCaIn)
	{
		this.payCollectCaIn = payCollectCaIn;
	}
	public BigDecimal getPayFine() {
		return payFine;
	}
	public void setPayFine(BigDecimal payFine) {
		this.payFine = payFine;
	}
	public BigDecimal getPayCollectFine() {
		return payCollectFine;
	}
	public void setPayCollectFine(BigDecimal payCollectFine) {
		this.payCollectFine = payCollectFine;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public BigDecimal getCollectServiceFee() {
		return collectServiceFee;
	}
	public void setCollectServiceFee(BigDecimal collectServiceFee) {
		this.collectServiceFee = collectServiceFee;
	}
	public BigDecimal getPayServiceFee() {
		return payServiceFee;
	}
	public void setPayServiceFee(BigDecimal payServiceFee) {
		this.payServiceFee = payServiceFee;
	}
}
