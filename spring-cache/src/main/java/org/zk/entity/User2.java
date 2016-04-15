package org.zk.entity;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhangkang on 2016/4/15.
 */
public class User2 {

    private Integer id;
    private String userName;
    private Integer age2;

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

    public Integer getAge2() {
        return age2;
    }

    public void setAge2(Integer age2) {
        this.age2 = age2;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
