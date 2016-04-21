package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * LoanDO abc_loan
 */
public class LoanDO {
    /**
     * 主键id abc_loan.loan_id
     */
    private Integer    loanId;

    /**
     * 项目logo abc_loan.loan_logo
     */
    private String     loanLogo;

    /**
     * 是否来自前台意向申请 abc_loan.loan_from_intent
     */
    private Boolean    loanFromIntent;

    /**
     * 意向编号 abc_loan.loan_intent_id
     */
    private Integer    loanIntentId;

    /**
     * 项目编号 abc_loan.loan_no
     */
    private String     loanNo;

    /**
     * 借款人类型 1:个人 2:企业 3:借款结构 4:平台 abc_loan.loan_emp_type
     */
    private Integer    loanEmpType;

    /**
     * 借款人id abc_loan.loan_user_id
     */
    private Integer    loanUserId;

    /**
     * 借款机构id 外键 abc_loan.loan_gov
     */
    private Integer    loanGov;

    /**
     * 担保机构id 外键 abc_loan.loan_guar_gov
     */
    private Integer    loanGuarGov;

    /**
     * 借款金额 abc_loan.loan_money
     */
    private BigDecimal loanMoney;

    /**
     * 年化收益率 abc_loan.loan_rate
     */
    private BigDecimal loanRate;

    /**
     * 借款期限 abc_loan.loan_period
     */
    private Integer    loanPeriod;

    /**
     * 期限类型 1:年 2:月 3:日 abc_loan.loan_period_type
     */
    private Integer    loanPeriodType;

    /**
     * 最低投标金额 abc_loan.loan_min_invest
     */
    private BigDecimal loanMinInvest;

    /**
     * 最高投标金额 abc_loan.loan_max_invest
     */
    private BigDecimal loanMaxInvest;

    /**
     * 当前投标总额 abc_loan.loan_current_invest
     */
    private BigDecimal loanCurrentInvest;

    /**
     * 当前有效投标总额 abc_loan.loan_current_valid_invest
     */
    private BigDecimal loanCurrentValidInvest;

    /**
     * 还款方式 1:等额本息 2:按月还息到月还本 3:等额本金 abc_loan.loan_pay_type
     */
    private Integer    loanPayType;

    /**
     * 项目类型 1:个人信用 2:抵押 3:担保 abc_loan.loan_type
     */
    private Integer    loanType;

    /**
     * 投资开始时间 abc_loan.loan_invest_starttime
     */
    private Date       loanInvestStarttime;

    /**
     * 投资结束时间 abc_loan.loan_invest_endtime
     */
    private Date       loanInvestEndtime;

    /**
     * 投资满标时间 abc_loan.loan_invest_fulltime
     */
    private Date       loanInvestFulltime;

    /**
     * 放款成功时间 abc_loan.loan_full_transferedtime
     */
    private Date       loanFullTransferedtime;

    /**
     * 结算方式 1:固定还款日 2:非固定还款日 abc_loan.loan_clear_type
     */
    private Integer    loanClearType;

    /**
     * 借款用途 abc_loan.loan_use
     */
    private String     loanUse;

    /**
     * 项目状态 -1:已删除 1:意向待审核 2:意向审核通过 3:意向审核未通过 4:待项目初审 5:项目初审通过 6:项目初审已退回
     * 7:项目初审未通过 8:待发布 9:招标中 10:满标待审 11:满标审核通过 12:满标审核未通过 13:已流标 14:划转中 15:还款中
     * 16:已结清 abc_loan.loan_state
     */
    private Integer    loanState;

    /**
     * 项目分类 1:企业经营贷 2:房屋抵押贷 3:汽车抵押贷 4:个人轻松贷 abc_loan.loan_category
     */
    private Integer    loanCategory;

    /**
     * 项目分类 外键 abc_loan.loan_category_id
     */
    private Integer    loanCategoryId;

    /**
     * 附件url abc_loan.loan_file_url
     */
    private String     loanFileUrl;

    /**
     * 添加人 外键 abc_loan.loan_creator
     */
    private Integer    loanCreator;

    /**
     * 修改人 外键 abc_loan.loan_modifier
     */
    private Integer    loanModifier;

    /**
     * 创建时间 abc_loan.loan_createtime
     */
    private Date       loanCreatetime;

    /**
     * 修改时间 abc_loan.loan_modifiytime
     */
    private Date       loanModifiytime;

    /**
     * 删除标记 0:未删除 1:已删除 abc_loan.loan_deleted
     */
    private Integer    loanDeleted;

    /**
     * 项目备注 abc_loan.loan_note
     */
    private String     loanNote;
    /**
     * 借款人简介
     */
    private String     borrowerIntroduction;
    /**
     * 风控信息
     */
    private String     riskIntroduction;
    /**
     * 相关文件
     */
    private String     relevantIntroduction;
    /**
     * 二次分配类型0:用户 1：担保机构2：不分配 abc_loan.loan_secondary_allocation
     */
    private String     loanSecondaryAllocation;
    //private Integer     loanSecondaryAllocation;

    /**
     * 指定还款日期： abc_loan.loan_pay_date
     */
    private Integer    loanPayDate;

    /**
     * 二次分配收款人 abc_loan.loan_secondary_user
     */
    private Integer    loanSecondaryUser;
    /**
     * 投资返送红包派发比例，按照1000整数倍派发。比例为零即不派发红包
     */
    private Double     investRedsendRatio;

    /**
     * 红包使用比例。比例为零即不可使用红包
     */
    private Double     investReduseRatio;
    /**
     * 红包使用范围
     */
    private String     loanRedUseScopes;
    /**
     * 指定到期日 abc_loan.loan_expire_date
     */
    private Date       loanExpireDate;

    /**
     * 合同保存的物理路径
     */
    private String     contractPath;

    public Integer getLoanSecondaryUser() {
        return loanSecondaryUser;
    }

    public void setLoanSecondaryUser(Integer loanSecondaryUser) {
        this.loanSecondaryUser = loanSecondaryUser;
    }

    public String getLoanSecondaryAllocation() {
        return loanSecondaryAllocation;
    }

    public void setLoanSecondaryAllocation(String loanSecondaryAllocation) {
        this.loanSecondaryAllocation = loanSecondaryAllocation;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getLoanLogo() {
        return loanLogo;
    }

    public void setLoanLogo(String loanLogo) {
        this.loanLogo = loanLogo;
    }

    public Boolean getLoanFromIntent() {
        return loanFromIntent;
    }

    public void setLoanFromIntent(Boolean loanFromIntent) {
        this.loanFromIntent = loanFromIntent;
    }

    public Integer getLoanIntentId() {
        return loanIntentId;
    }

    public void setLoanIntentId(Integer loanIntentId) {
        this.loanIntentId = loanIntentId;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoanEmpType() {
        return loanEmpType;
    }

    public void setLoanEmpType(Integer loanEmpType) {
        this.loanEmpType = loanEmpType;
    }

    public Integer getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Integer loanUserId) {
        this.loanUserId = loanUserId;
    }

    public Integer getLoanGov() {
        return loanGov;
    }

    public void setLoanGov(Integer loanGov) {
        this.loanGov = loanGov;
    }

    public Integer getLoanGuarGov() {
        return loanGuarGov;
    }

    public void setLoanGuarGov(Integer loanGuarGov) {
        this.loanGuarGov = loanGuarGov;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Integer getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(Integer loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public BigDecimal getLoanMinInvest() {
        return loanMinInvest;
    }

    public void setLoanMinInvest(BigDecimal loanMinInvest) {
        this.loanMinInvest = loanMinInvest;
    }

    public BigDecimal getLoanMaxInvest() {
        return loanMaxInvest;
    }

    public void setLoanMaxInvest(BigDecimal loanMaxInvest) {
        this.loanMaxInvest = loanMaxInvest;
    }

    public BigDecimal getLoanCurrentInvest() {
        return loanCurrentInvest;
    }

    public void setLoanCurrentInvest(BigDecimal loanCurrentInvest) {
        this.loanCurrentInvest = loanCurrentInvest;
    }

    public BigDecimal getLoanCurrentValidInvest() {
        return loanCurrentValidInvest;
    }

    public void setLoanCurrentValidInvest(BigDecimal loanCurrentValidInvest) {
        this.loanCurrentValidInvest = loanCurrentValidInvest;
    }

    public Integer getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(Integer loanPayType) {
        this.loanPayType = loanPayType;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public Date getLoanInvestStarttime() {
        return loanInvestStarttime;
    }

    public void setLoanInvestStarttime(Date loanInvestStarttime) {
        this.loanInvestStarttime = loanInvestStarttime;
    }

    public Date getLoanInvestEndtime() {
        return loanInvestEndtime;
    }

    public void setLoanInvestEndtime(Date loanInvestEndtime) {
        this.loanInvestEndtime = loanInvestEndtime;
    }

    public Date getLoanInvestFulltime() {
        return loanInvestFulltime;
    }

    public void setLoanFullTransferedtime(Date loanFullTransferedtime) {
        this.loanFullTransferedtime = loanFullTransferedtime;
    }

    public Date getLoanFullTransferedtime() {
        return loanFullTransferedtime;
    }

    public void setLoanInvestFulltime(Date loanInvestFulltime) {
        this.loanInvestFulltime = loanInvestFulltime;
    }

    public Integer getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(Integer loanClearType) {
        this.loanClearType = loanClearType;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public Integer getLoanState() {
        return loanState;
    }

    public void setLoanState(Integer loanState) {
        this.loanState = loanState;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Integer getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(Integer loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanFileUrl() {
        return loanFileUrl;
    }

    public void setLoanFileUrl(String loanFileUrl) {
        this.loanFileUrl = loanFileUrl;
    }

    public Integer getLoanCreator() {
        return loanCreator;
    }

    public void setLoanCreator(Integer loanCreator) {
        this.loanCreator = loanCreator;
    }

    public Integer getLoanModifier() {
        return loanModifier;
    }

    public void setLoanModifier(Integer loanModifier) {
        this.loanModifier = loanModifier;
    }

    public Date getLoanCreatetime() {
        return loanCreatetime;
    }

    public void setLoanCreatetime(Date loanCreatetime) {
        this.loanCreatetime = loanCreatetime;
    }

    public Date getLoanModifiytime() {
        return loanModifiytime;
    }

    public void setLoanModifiytime(Date loanModifiytime) {
        this.loanModifiytime = loanModifiytime;
    }

    public Integer getLoanDeleted() {
        return loanDeleted;
    }

    public void setLoanDeleted(Integer loanDeleted) {
        this.loanDeleted = loanDeleted;
    }

    public String getLoanNote() {
        return loanNote;
    }

    public void setLoanNote(String loanNote) {
        this.loanNote = loanNote;
    }

    public Integer getLoanPayDate() {
        return loanPayDate;
    }

    public void setLoanPayDate(Integer loanPayDate) {
        this.loanPayDate = loanPayDate;
    }

    public Date getLoanExpireDate() {
        return loanExpireDate;
    }

    public void setLoanExpireDate(Date loanExpireDate) {
        this.loanExpireDate = loanExpireDate;
    }

    public Double getInvestRedsendRatio() {
        return investRedsendRatio;
    }

    public void setInvestRedsendRatio(Double investRedsendRatio) {
        this.investRedsendRatio = investRedsendRatio;
    }

    public Double getInvestReduseRatio() {
        return investReduseRatio;
    }

    public void setInvestReduseRatio(Double investReduseRatio) {
        this.investReduseRatio = investReduseRatio;
    }

    public String getBorrowerIntroduction() {
        return borrowerIntroduction;
    }

    public void setBorrowerIntroduction(String borrowerIntroduction) {
        this.borrowerIntroduction = borrowerIntroduction;
    }

    public String getRiskIntroduction() {
        return riskIntroduction;
    }

    public void setRiskIntroduction(String riskIntroduction) {
        this.riskIntroduction = riskIntroduction;
    }

    public String getRelevantIntroduction() {
        return relevantIntroduction;
    }

    public void setRelevantIntroduction(String relevantIntroduction) {
        this.relevantIntroduction = relevantIntroduction;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getLoanRedUseScopes() {
        return loanRedUseScopes;
    }

    public void setLoanRedUseScopes(String loanRedUseScopes) {
        this.loanRedUseScopes = loanRedUseScopes;
    }

}
