package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 用户（网友）信息
 * 
 * @author RJQ 2014/11/13 18:15.
 */
public class UserDO {
    /**
     * 主键 abc_user.user_id
     */
    private Integer    userId;
    /**
     * 用户对应的uuid  abc_user.user_uuid
     */
    private String    userUuid;

    /**
     * 用户名 abc_user.user_name
     */
    private String     userName;

    /**
     * 真实姓名 abc_user.user_real_name
     */
    private String     userRealName;

    /**
     * 登录密码 abc_user.user_pwd
     */
    private String     userPwd;

    /**
     * 交易密码 abc_user.user_deal_pwd
     */
    private String     userDealPwd;

    /**
     * 用户类型 1：个人 2：企业 abc_user.user_type
     */
    private Integer    userType;

    /**
     * 证件类型 abc_user.user_doc_type
     */
    private String     userDocType;

    /**
     * 证件号码 abc_user.user_doc_no
     */
    private String     userDocNo;

    /**
     * 性别 1:男 0:女 abc_user.user_sex
     */
    private Integer    userSex;

    /**
     * 生日 abc_user.user_birthday
     */
    private Date       userBirthday;

    /**
     * 民族 abc_user.user_nation
     */
    private String     userNation;

    /**
     * 籍贯 abc_user.user_native
     */
    private String     userNative;

    /**
     * 手机号码 abc_user.user_phone
     */
    private String     userPhone;

    /**
     * 电子邮箱 abc_user.user_email
     */
    private String     userEmail;

    /**
     * 婚姻状况 1:已婚 2:未婚 3:离婚 4:丧偶 abc_user.user_marry
     */
    private Integer    userMarry;

    /**
     * 月收入 abc_user.user_month_income
     */
    private BigDecimal userMonthIncome;

    /**
     * 登录次数 abc_user.user_login_count
     */
    private Integer    userLoginCount;

    /**
     * 网站头像 abc_user.user_head_img
     */
    private String     userHeadImg;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer    userScore;

    /**
     * 积分最后修改时间 abc_user.user_score_lastmodifytime
     */
    private Date       userScoreLastmodifytime;

    /**
     * 是否启用 1：是 0：否 2:已删除 abc_user.user_state
     */
    private Integer    userState;

    /**
     * 是否开启自动投标计划 1：开启 0：未开启 abc_user.user_auto_invest
     */
    private Integer    userAutoInvest;

    /**
     * 推荐人id abc_user.user_recommend_userid
     */
    private Integer    userRecommendUserid;

    /**
     * 信用额度 abc_user.user_loan_credit
     */
    private BigDecimal userLoanCredit;

    /**
     * 可用信用额度 abc_user.user_credit_sett
     */
    private BigDecimal userCreditSett;

    /**
     * 注册日期 abc_user.user_register_date
     */
    private Date       userRegisterDate;

    /**
     * 是否实名认证 1：是 0：否 abc_user.user_realname_isproven
     */
    private Integer    userRealnameIsproven;

    /**
     * 是否绑定手机号 1：是 0：否 abc_user.user_mobile_isbinded
     */
    private Integer    userMobileIsbinded;

    /**
     * 是否绑定邮箱 1：是 0：否 abc_user.user_email_isbinded
     */
    private Integer    userEmailIsbinded;

    /**
     * 是否绑定银行卡 1：是 0：否 abc_user.user_bankcard_isbinded
     */
    private Integer    userBankcardIsbinded;

    /**
     * 邮箱激活时验证码 abc_user.user_email_verify_code
     */
    private String     userEmailVerifyCode;

    /**
     * 邮箱激活时时间 abc_user.user_email_verify_date
     */
    private Date       userEmailVerifyDate;

    /**
     * 手机认证日期 abc_user.user_mobile_verify_date
     */
    private Date       userMobileVerifyDate;

    /**
     * 1：已在线 0：未在线 abc_user.user_isonline
     */
    private Integer    userIsonline;

    /**
     * 业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资
     */
    private Integer    userBusinessState;
    /**
     * 是否开启自动转账授权 0 未开启 1 已开启
     */
    private Integer    userAuthorizeFlag;
    
    /**
     * 免费提现额度 abc_user.user_cash_quota
     */
    private BigDecimal userCashQuota;
    
    /**
     * 微信号ID abc_user.user_wechatid
     */
    private String     userWeChatID;

    /**
     * 微信账号 abc_user.user_wechatname
     */
    private String     userWeChatName;

    /**
     * 是否绑定微信号;1表示已绑定,0表示未绑定 abc_user.user_isbound
     */
    private Integer    userIsBound;

    /**
     * 是否免登录;0否，1是 abc_user.user_free_login
     */
    private Integer    userFreeLogin;

    /**
     * 用户是否投资过新手标;1表示投资过，0表示没有投资过 abc_user.user_novice_loan
     */
    private Integer    userNoviceLoan;
    /**
     * 用户错误登录次数
     */
    private Integer userErrorCount = 0;
    /**
     * 用户上次登录错误时间
     */
    private Date userErrorTime;
    
	public Integer getUserErrorCount() {
		return userErrorCount;
	}

	public void setUserErrorCount(Integer userErrorCount) {
		this.userErrorCount = userErrorCount;
	}

	public Date getUserErrorTime() {
		return userErrorTime;
	}

	public void setUserErrorTime(Date userErrorTime) {
		this.userErrorTime = userErrorTime;
	}

	public Integer getUserBusinessState() {
        return userBusinessState;
    }

    public void setUserBusinessState(Integer userBusinessState) {
        this.userBusinessState = userBusinessState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserDealPwd() {
        return userDealPwd;
    }

    public void setUserDealPwd(String userDealPwd) {
        this.userDealPwd = userDealPwd;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserDocType() {
        return userDocType;
    }

    public void setUserDocType(String userDocType) {
        this.userDocType = userDocType;
    }

    public String getUserDocNo() {
        return userDocNo;
    }

    public void setUserDocNo(String userDocNo) {
        this.userDocNo = userDocNo;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation;
    }

    public String getUserNative() {
        return userNative;
    }

    public void setUserNative(String userNative) {
        this.userNative = userNative;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserMarry() {
        return userMarry;
    }

    public void setUserMarry(Integer userMarry) {
        this.userMarry = userMarry;
    }

    public BigDecimal getUserMonthIncome() {
        return userMonthIncome;
    }

    public void setUserMonthIncome(BigDecimal userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Date getUserScoreLastmodifytime() {
        return userScoreLastmodifytime;
    }

    public void setUserScoreLastmodifytime(Date userScoreLastmodifytime) {
        this.userScoreLastmodifytime = userScoreLastmodifytime;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getUserAutoInvest() {
        return userAutoInvest;
    }

    public void setUserAutoInvest(Integer userAutoInvest) {
        this.userAutoInvest = userAutoInvest;
    }

    public Integer getUserRecommendUserid() {
        return userRecommendUserid;
    }

    public void setUserRecommendUserid(Integer userRecommendUserid) {
        this.userRecommendUserid = userRecommendUserid;
    }

    public BigDecimal getUserLoanCredit() {
        return userLoanCredit;
    }

    public void setUserLoanCredit(BigDecimal userLoanCredit) {
        this.userLoanCredit = userLoanCredit;
    }

    public BigDecimal getUserCreditSett() {
        return userCreditSett;
    }

    public void setUserCreditSett(BigDecimal userCreditSett) {
        this.userCreditSett = userCreditSett;
    }

    public Date getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(Date userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public Integer getUserRealnameIsproven() {
        return userRealnameIsproven;
    }

    public void setUserRealnameIsproven(Integer userRealnameIsproven) {
        this.userRealnameIsproven = userRealnameIsproven;
    }

    public Integer getUserMobileIsbinded() {
        return userMobileIsbinded;
    }

    public void setUserMobileIsbinded(Integer userMobileIsbinded) {
        this.userMobileIsbinded = userMobileIsbinded;
    }

    public Integer getUserEmailIsbinded() {
        return userEmailIsbinded;
    }

    public void setUserEmailIsbinded(Integer userEmailIsbinded) {
        this.userEmailIsbinded = userEmailIsbinded;
    }

    public Integer getUserBankcardIsbinded() {
        return userBankcardIsbinded;
    }

    public void setUserBankcardIsbinded(Integer userBankcardIsbinded) {
        this.userBankcardIsbinded = userBankcardIsbinded;
    }

    public String getUserEmailVerifyCode() {
        return userEmailVerifyCode;
    }

    public void setUserEmailVerifyCode(String userEmailVerifyCode) {
        this.userEmailVerifyCode = userEmailVerifyCode;
    }

    public Date getUserEmailVerifyDate() {
        return userEmailVerifyDate;
    }

    public void setUserEmailVerifyDate(Date userEmailVerifyDate) {
        this.userEmailVerifyDate = userEmailVerifyDate;
    }

    public Date getUserMobileVerifyDate() {
        return userMobileVerifyDate;
    }

    public void setUserMobileVerifyDate(Date userMobileVerifyDate) {
        this.userMobileVerifyDate = userMobileVerifyDate;
    }

    public Integer getUserIsonline() {
        return userIsonline;
    }

    public void setUserIsonline(Integer userIsonline) {
        this.userIsonline = userIsonline;
    }

    public Integer getUserAuthorizeFlag() {
        return userAuthorizeFlag;
    }

    public void setUserAuthorizeFlag(Integer userAuthorizeFlag) {
        this.userAuthorizeFlag = userAuthorizeFlag;
    }

	public BigDecimal getUserCashQuota() {
		return userCashQuota;
	}

	public void setUserCashQuota(BigDecimal userCashQuota) {
		this.userCashQuota = userCashQuota;
	}

	/**
	 * @return the userWeChatID
	 */
	public String getUserWeChatID() {
		return userWeChatID;
	}

	/**
	 * @param userWeChatID the userWeChatID to set
	 */
	public void setUserWeChatID(String userWeChatID) {
		this.userWeChatID = userWeChatID;
	}

	/**
	 * @return the userWeChatName
	 */
	public String getUserWeChatName() {
		return userWeChatName;
	}

	/**
	 * @param userWeChatName the userWeChatName to set
	 */
	public void setUserWeChatName(String userWeChatName) {
		this.userWeChatName = userWeChatName;
	}

	/**
	 * @return the userIsBound
	 */
	public Integer getUserIsBound() {
		return userIsBound;
	}

	/**
	 * @param userIsBound the userIsBound to set
	 */
	public void setUserIsBound(Integer userIsBound) {
		this.userIsBound = userIsBound;
	}

	/**
	 * @return the userFreeLogin
	 */
	public Integer getUserFreeLogin() {
		return userFreeLogin;
	}

	/**
	 * @param userFreeLogin the userFreeLogin to set
	 */
	public void setUserFreeLogin(Integer userFreeLogin) {
		this.userFreeLogin = userFreeLogin;
	}

	/**
	 * @return the userNoviceLoan
	 */
	public Integer getUserNoviceLoan() {
		return userNoviceLoan;
	}

	/**
	 * @param userNoviceLoan the userNoviceLoan to set
	 */
	public void setUserNoviceLoan(Integer userNoviceLoan) {
		this.userNoviceLoan = userNoviceLoan;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
