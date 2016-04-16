package org.zk.entity;

import com.alibaba.fastjson.JSON;
import org.zhangkang.commons.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/2.
 */
public class User extends BaseEntity {
    private String userName;
    private String password;
    private Integer age;
    private Date birthday;

    private Classes classes;
    private List<Course> courseList;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
