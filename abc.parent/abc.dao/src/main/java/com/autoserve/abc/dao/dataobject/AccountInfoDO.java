package com.autoserve.abc.dao.dataobject;

/**
 * 用户账户
 */
public class AccountInfoDO {
    /**
     * 主键id abc_account_info.account_id
     */
    private Integer accountId;

    /**
     * 用户id abc_account_info.account_user_id
     */
    private Integer accountUserId;

    /**
     * 用户类型 abc_account_info.account_user_type
     */
    private Integer accountUserType;

    /**
     * 法人姓名 abc_account_info.account_legal_name
     */
    private String  accountLegalName;

    /**
     * 用户姓名(企业名称) abc_account_info.account_user_name
     */
    private String  accountUserName;

    /**
     * 身份证号(法人身份证号) abc_account_info.account_user_card
     */
    private String  accountUserCard;

    /**
     * 开户账户 abc_account_info.account_no
     */
    private String  accountNo;

    /**
     * 手机号码(法人手机号码) abc_account_info.account_user_phone
     */
    private String  accountUserPhone;

    /**
     * 邮箱地址(法人邮箱地址) abc_account_info.account_user_email
     */
    private String  accountUserEmail;

    /**
     * 开户银行地区 abc_account_info.account_bank_area
     */
    private String  accountBankArea;

    /**
     * 开户银行名称 abc_account_info.account_bank_name
     */
    private String  accountBankName;

    /**
     * 开户银行支行名称 abc_account_info.account_bank_branch_name
     */
    private String  accountBankBranchName;

    /**
     * 帐号(企业对公账户号) abc_account_info.account_user_account
     */
    private String  accountUserAccount;

    /**
     * 提现密码 abc_account_info.account_cash_pwd
     */
    private String  accountCashPwd;

    /**
     * 登录密码 abc_account_info.account_login_pwd
     */
    private String  accountLoginPwd;

    /**
     * 备注 abc_account_info.account_mark
     */
    private String  accountMark;

    /**
     * 删除标识 0未删除 -1已删除
     */
    private Integer accountDelFlag;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountUserId() {
        return accountUserId;
    }

    public void setAccountUserId(Integer accountUserId) {
        this.accountUserId = accountUserId;
    }

    public Integer getAccountUserType() {
        return accountUserType;
    }

    public void setAccountUserType(Integer accountUserType) {
        this.accountUserType = accountUserType;
    }

    public String getAccountLegalName() {
        return accountLegalName;
    }

    public void setAccountLegalName(String accountLegalName) {
        this.accountLegalName = accountLegalName;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getAccountUserCard() {
        return accountUserCard;
    }

    public void setAccountUserCard(String accountUserCard) {
        this.accountUserCard = accountUserCard;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountUserPhone() {
        return accountUserPhone;
    }

    public void setAccountUserPhone(String accountUserPhone) {
        this.accountUserPhone = accountUserPhone;
    }

    public String getAccountUserEmail() {
        return accountUserEmail;
    }

    public void setAccountUserEmail(String accountUserEmail) {
        this.accountUserEmail = accountUserEmail;
    }

    public String getAccountBankArea() {
        return accountBankArea;
    }

    public void setAccountBankArea(String accountBankArea) {
        this.accountBankArea = accountBankArea;
    }

    public String getAccountBankName() {
        return accountBankName;
    }

    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    public String getAccountBankBranchName() {
        return accountBankBranchName;
    }

    /**
     * @param String accountBankBranchName
     *            (abc_account_info.account_bank_branch_name )
     */
    public void setAccountBankBranchName(String accountBankBranchName) {
        this.accountBankBranchName = accountBankBranchName;
    }

    public String getAccountUserAccount() {
        return accountUserAccount;
    }

    public void setAccountUserAccount(String accountUserAccount) {
        this.accountUserAccount = accountUserAccount;
    }

    public String getAccountCashPwd() {
        return accountCashPwd;
    }

    public void setAccountCashPwd(String accountCashPwd) {
        this.accountCashPwd = accountCashPwd;
    }

    public String getAccountLoginPwd() {
        return accountLoginPwd;
    }

    public void setAccountLoginPwd(String accountLoginPwd) {
        this.accountLoginPwd = accountLoginPwd;
    }

    public String getAccountMark() {
        return accountMark;
    }

    public void setAccountMark(String accountMark) {
        this.accountMark = accountMark;
    }

    /**
     * @return the accountDelFlag
     */
    public Integer getAccountDelFlag() {
        return accountDelFlag;
    }

    /**
     * @param accountDelFlag the accountDelFlag to set
     */
    public void setAccountDelFlag(Integer accountDelFlag) {
        this.accountDelFlag = accountDelFlag;
    }
}
