package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：用户类别
 *
 * @author RJQ 2014/11/17 20:25.
 */
public enum UserType {

    PERSONAL(1, "个人用户"),
    ENTERPRISE(2, "企业用户"),
    PARTNER(3, "平台加盟商");

    UserType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public static UserType valueOf(Integer type) {
        for (UserType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public final int    type;
    public final String des;
}
