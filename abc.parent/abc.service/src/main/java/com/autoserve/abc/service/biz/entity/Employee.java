package com.autoserve.abc.service.biz.entity;

import java.util.Date;

public class Employee {
    /**
     * 主键
     */
    private Integer empId;

    /**
     * 用户名
     */
    private String empName;

    /**
     * 真实姓名
     */
    private String empRealName;

    /**
     * 角色
     */
    private String empRole;

    /**
     * 手机号码
     */
    private String empMobile;

    /**
     * 状态
     */
    private Integer empState;

    /**
     * 员工工号
     */
    private String empNo;

    /**
     * 电子邮箱
     */
    private String empEmail;

    /**
     * 照片
     */
    private String empPhoto;

    /**
     * 登录次数
     */
    private Integer empLoginCount;

    /**
     * 最近一次登录时间
     */
    private Date empLastlogintime;

    /**
     * 头像
     */
    private String empHeadImg;

    public Employee() {
    }

    public Employee(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public Integer getEmpLoginCount() {
        return empLoginCount;
    }

    public void setEmpLoginCount(Integer empLoginCount) {
        this.empLoginCount = empLoginCount;
    }

    public Date getEmpLastlogintime() {
        return empLastlogintime;
    }

    public void setEmpLastlogintime(Date empLastlogintime) {
        this.empLastlogintime = empLastlogintime;
    }

    public String getEmpHeadImg() {
        return empHeadImg;
    }

    public void setEmpHeadImg(String empHeadImg) {
        this.empHeadImg = empHeadImg;
    }

    public String getEmpRealName() {
        return empRealName;
    }

    public void setEmpRealName(String empRealName) {
        this.empRealName = empRealName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public Integer getEmpState() {
        return empState;
    }

    public void setEmpState(Integer empState) {
        this.empState = empState;
    }
}
