package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.EmailState;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.MaritalStatus;
import com.autoserve.abc.service.biz.enums.MobileState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;

public class User implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3386151974478525909L;

    /**
     * 主键 abc_user.user_id
     */
    private Integer           userId;
    
    /**
     * 用户对应的uuid  abc_user.user_uuid
     */
    private String    userUuid;

    /**
     * 用户名 abc_user.user_name
     */
    private String            userName;

    /**
     * 真实姓名 abc_user.user_real_name
     */
    private String            userRealName;

    /**
     * 用户类型 1：个人 2：企业 abc_user.user_type
     */
    private UserType          userType;

    /**
     * 证件类型 abc_user.user_doc_type
     */
    private String            userDocType;

    /**
     * 证件号码 abc_user.user_doc_no
     */
    private String            userDocNo;

    /**
     * 性别 1:男 0:女 abc_user.user_sex
     */
    private SexType           userSex;

    /**
     * 生日 abc_user.user_birthday
     */
    private Date              userBirthday;

    /**
     * 籍贯 abc_user.user_native
     */
    private String            userNative;

    /**
     * 手机号码 abc_user.user_phone
     */
    private String            userPhone;

    /**
     * 电子邮箱 abc_user.user_email
     */
    private String            userEmail;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer           userScore;

    /**
     * 积分最后修改时间 abc_user.user_score_lastmodifytime
     */
    private Date              userScoreLastmodifytime;

    /**
     * 是否启用 1：是 0：否 2:已删除 abc_user.user_state
     */
    private EntityState       userState;

    /**
     * 婚姻状况 1:已婚 2:未婚 3:离婚 4:丧偶 abc_user.user_marry
     */
    private MaritalStatus     userMarry;

    /**
     * 月收入 abc_user.user_month_income
     */
    private BigDecimal        userMonthIncome;

    /**
     * 注册日期 abc_user.user_register_date
     */
    private Date              userRegisterDate;
    /**
     * 手机绑定状态：1：已绑定 0：未绑定 abc_user.user_mobile_isbinded
     */
    private MobileState       userMobileIsbinded;
    /**
     * 邮箱绑定状态： 1：已绑定 0:未绑定 abc_user.user_email_isbinded
     */
    private EmailState        userEmailIsbinded;
    /**
     * 手机认证日期abc_user.user_mobile_verify_date
     */
    private Date              userMobileVerifyDate;

    /**
     * 业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资
     */
    private UserBusinessState userBusinessState;
    /**
     * 是否开启自动转账授权 0 未开启 1 已开启
     */
    private UserAuthorizeFlag userAuthorizeFlag;

    /**
     * 免费提现额度 abc_user.user_cash_quota
     */
    private BigDecimal userCashQuota;
    /**
     * 用户头像
     */
    private String userHeadImg;
    /**
     * 是否实名认证 1：是 0：否 abc_user.user_realname_isproven
     */
    private Integer    userRealnameIsproven;
    
    
	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public UserBusinessState getUserBusinessState() {
        return userBusinessState;
    }

    public void setUserBusinessState(UserBusinessState userBusinessState) {
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
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

    public SexType getUserSex() {
        return userSex;
    }

    public void setUserSex(SexType userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
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

    public EntityState getUserState() {
        return userState;
    }

    public void setUserState(EntityState userState) {
        this.userState = userState;
    }

    public MaritalStatus getUserMarry() {
        return userMarry;
    }

    public void setUserMarry(MaritalStatus userMarry) {
        this.userMarry = userMarry;
    }

    public BigDecimal getUserMonthIncome() {
        return userMonthIncome;
    }

    public void setUserMonthIncome(BigDecimal userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

    public Date getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(Date userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public MobileState getUserMobileIsbinded() {
        return userMobileIsbinded;
    }

    public void setUserMobileIsbinded(MobileState userMobileIsbinded) {
        this.userMobileIsbinded = userMobileIsbinded;
    }

    public EmailState getUserEmailIsbinded() {
        return userEmailIsbinded;
    }

    public void setUserEmailIsbinded(EmailState userEmailIsbinded) {
        this.userEmailIsbinded = userEmailIsbinded;
    }

    public Date getUserMobileVerifyDate() {
        return userMobileVerifyDate;
    }

    public void setUserMobileVerifyDate(Date userMobileVerifyDate) {
        this.userMobileVerifyDate = userMobileVerifyDate;
    }

    public UserAuthorizeFlag getUserAuthorizeFlag() {
        return userAuthorizeFlag;
    }

    public void setUserAuthorizeFlag(UserAuthorizeFlag userAuthorizeFlag) {
        this.userAuthorizeFlag = userAuthorizeFlag;
    }

	public BigDecimal getUserCashQuota() {
		return userCashQuota;
	}

	public void setUserCashQuota(BigDecimal userCashQuota) {
		this.userCashQuota = userCashQuota;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public Integer getUserRealnameIsproven() {
		return userRealnameIsproven;
	}

	public void setUserRealnameIsproven(Integer userRealnameIsproven) {
		this.userRealnameIsproven = userRealnameIsproven;
	}
	
}
