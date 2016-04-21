package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 员工
 *
 * @author RJQ 2014/11/13 18:05.
 */
public class EmployeeDO {
    /**
     * 主键
     * abc_employee.emp_id
     */
    private Integer empId;

    /**
     * 用户名
     * abc_employee.emp_name
     */
    private String empName;

    /**
     * 真实姓名
     * abc_employee.emp_real_name
     */
    private String empRealName;

    /**
     * 密码
     * abc_employee.emp_password
     */
    private String empPassword;

    /**
     * 员工工号
     * abc_employee.emp_no
     */
    private String empNo;

    /**
     * 证件类型
     * abc_employee.emp_doc_type
     */
    private String empDocType;

    /**
     * 证件号码
     * abc_employee.emp_doc_no
     */
    private String empDocNo;

    /**
     * 电子邮箱
     * abc_employee.emp_email
     */
    private String empEmail;

    /**
     * 出生日期
     * abc_employee.emp_birthday
     */
    private Date empBirthday;

    /**
     * 参加工作时间
     * abc_employee.emp_worktime
     */
    private Date empWorktime;

    /**
     * 入职时间
     * abc_employee.emp_entrytime
     */
    private Date empEntrytime;

    /**
     * 手机号码
     * abc_employee.emp_mobile
     */
    private String empMobile;

    /**
     * 家庭电话
     * abc_employee.emp_phone
     */
    private String empPhone;

    /**
     * 照片
     * abc_employee.emp_photo
     */
    private String empPhoto;

    /**
     * 详细地址
     * abc_employee.emp_address
     */
    private String empAddress;

    /**
     * 备注
     * abc_employee.emp_note
     */
    private String empNote;

    /**
     * 登录次数
     * abc_employee.emp_login_count
     */
    private Integer empLoginCount;

    /**
     * 1：平台用户 2：小贷/担保用户
     * abc_employee.emp_type
     */
    private Integer empType;

    /**
     * 0：停用 1：启用 -1:已删除
     * abc_employee.emp_state
     */
    private Integer empState;

    /**
     * 所属地区
     * abc_employee.emp_area
     */
    private String empArea;

    /**
     * 创建时间
     * abc_employee.emp_createtime
     */
    private Date empCreatetime;

    /**
     * 最近一次登录时间
     * abc_employee.emp_lastlogintime
     */
    private Date empLastlogintime;

    /**
     * 机构ID
     * abc_employee.emp_org_id
     */
    private Integer empOrgId;

    /**
     * 0：女 1：男
     * abc_employee.emp_sex
     */
    private Integer empSex;

    /**
     * QQ
     * abc_employee.emp_qq
     */
    private String empQq;

    /**
     * 头像
     * abc_employee.emp_head_img
     */
    private String empHeadImg;

    /**
     * 1：在线 0：离线
     * abc_employee.emp_isonline
     */
    private Integer empIsonline;

    private Date empLastModifyTime;

    public Date getEmpLastModifyTime() {
        return empLastModifyTime;
    }

    public void setEmpLastModifyTime(Date empLastModifyTime) {
        this.empLastModifyTime = empLastModifyTime;
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

    public String getEmpRealName() {
        return empRealName;
    }

    public void setEmpRealName(String empRealName) {
        this.empRealName = empRealName;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpDocType() {
        return empDocType;
    }

    public void setEmpDocType(String empDocType) {
        this.empDocType = empDocType;
    }

    public String getEmpDocNo() {
        return empDocNo;
    }

    public void setEmpDocNo(String empDocNo) {
        this.empDocNo = empDocNo;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public Date getEmpBirthday() {
        return empBirthday;
    }

    public void setEmpBirthday(Date empBirthday) {
        this.empBirthday = empBirthday;
    }

    public Date getEmpWorktime() {
        return empWorktime;
    }

    public void setEmpWorktime(Date empWorktime) {
        this.empWorktime = empWorktime;
    }

    public Date getEmpEntrytime() {
        return empEntrytime;
    }

    public void setEmpEntrytime(Date empEntrytime) {
        this.empEntrytime = empEntrytime;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpNote() {
        return empNote;
    }

    public void setEmpNote(String empNote) {
        this.empNote = empNote;
    }

    public Integer getEmpLoginCount() {
        return empLoginCount;
    }

    public void setEmpLoginCount(Integer empLoginCount) {
        this.empLoginCount = empLoginCount;
    }

    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public Integer getEmpState() {
        return empState;
    }

    public void setEmpState(Integer empState) {
        this.empState = empState;
    }

    public String getEmpArea() {
        return empArea;
    }

    public void setEmpArea(String empArea) {
        this.empArea = empArea;
    }

    public Date getEmpCreatetime() {
        return empCreatetime;
    }

    public void setEmpCreatetime(Date empCreatetime) {
        this.empCreatetime = empCreatetime;
    }

    public Date getEmpLastlogintime() {
        return empLastlogintime;
    }

    public void setEmpLastlogintime(Date empLastlogintime) {
        this.empLastlogintime = empLastlogintime;
    }

    public Integer getEmpOrgId() {
        return empOrgId;
    }

    public void setEmpOrgId(Integer empOrgId) {
        this.empOrgId = empOrgId;
    }

    public Integer getEmpSex() {
        return empSex;
    }

    public void setEmpSex(Integer empSex) {
        this.empSex = empSex;
    }

    public String getEmpQq() {
        return empQq;
    }

    public void setEmpQq(String empQq) {
        this.empQq = empQq;
    }

    public String getEmpHeadImg() {
        return empHeadImg;
    }

    public void setEmpHeadImg(String empHeadImg) {
        this.empHeadImg = empHeadImg;
    }

    public Integer getEmpIsonline() {
        return empIsonline;
    }

    public void setEmpIsonline(Integer empIsonline) {
        this.empIsonline = empIsonline;
    }
}
