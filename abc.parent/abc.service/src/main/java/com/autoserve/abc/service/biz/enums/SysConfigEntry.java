package com.autoserve.abc.service.biz.enums;

/**
 * @author yuqing.zheng Created on 2014-11-26,18:14
 */
public enum SysConfigEntry {
    SHUTDOWN_SITE("关闭站点"),
    SHUTDOWN_INFO("网站关闭信息"),
    MIN_CREDIT_LIMIT("起始最低信用额度"),
    PUNISH_INTEREST_RATE("罚息利率"),
    CONTINUE_DESIGNATED_PAY("上期代还未收回是否继续代还"),
    LOAN_TRANSFER_LIMIT("债权转让次数"),
    LOAN_TRANSFER_DISCONT_MIN("最小债权转让折扣"),
    LOAN_TRANSFER_DISCONT_MAX("最大债权转让折扣"),
    LOAN_TRANSFER_FEE_RATE("转让手续费率"),
    LOAN_TRANSFER_PERIOD("首次债权转让距放款周期"),
    LOAN_TRANSFER_PERIOD_TYPE("首次债权转让距放款周期类型"),
    LOAN_LIMIT_PER_DAY("个体每日融资项目数"),
    MAIL_SMTP_SERVER("SMTP服务器"),
    MAIL_PORT("端口号"),
    MAIL_ADDRESS("邮箱地址"),
    MAIL_PASSWORD("邮箱密码"),
    MAIL_SENDER_NAME("发件人昵称或姓名"),
    SMS_USER("短信用户名"),
    SMS_PASSWORD("短信密码"),
    EMPLOYEE_DEFAULT_PASSWORD("新员工默认密码"),
    SCORE_USAGE_VALID_PERIOD("积分投资券默认使用期限"), //单位月
    PLATFORM_ACCOUNT("平台帐户"),
    PRO_NUMBER("项目编号初始值"),
    NUMBER_YEAR("项目编号年份初始值"),
    NUMBER_RULE("项目编号模板配置"),
    EXPIRE_REMIND("距到期提醒天数"),

    INVEST_RED_VAKIDITY("投资返送红包有效期"),

    ENTERPRISE_NAME("公司名称"),
    ENTERPRISE_ADDRESS("公司地址"),
    ENTERPRISE_WEBSITE("公司网址"),
    
    SMS_NOTIFY_INVEST_CFG("投资成功提醒"),
    SMS_NOTIFY_SPECIAL_TRANSFER_CFG("转让标资金划转成功提醒"),
    SMS_NOTIFY_COMMON_TRANSFER_CFG("普通标资金划转成功提醒"),
    SMS_NOTIFY_REPAYMENT_CFG("还款成功提醒"),
    MONTHFREE_TOCASH_TIMES("每个用户每月免费提现次数"),
    WAIT_PAY_CAPITAL("借款人使用免费提现次数的限制，待还本金的上限"),
    
    RED_SPLIT_BASE("投资返红包拆分基数"),
    
    LOGIN_COUNT_LIMIT("用户错误登录次数限制"),
    LOGIN_TIME_LIMIT("用户登录错误，锁定账户时间");
    
    public final String descrip;

    SysConfigEntry(String descrip) {
        this.descrip = descrip;
    }

}
