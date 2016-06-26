package org.zhangkang.entity;

import java.util.Date;import com.alibaba.fastjson.annotation.JSONField;/**
 * Created by Administrator on 2016/4/23.
 */
public class User extends BaseEntity {
    private Integer id;
   @JSONField(serialize = false)
    private String userName;
    private Integer age;
    private Date birthDay;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
