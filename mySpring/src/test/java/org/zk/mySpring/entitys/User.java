package org.zk.mySpring.entitys;

import org.zhangkang.entity.BaseEntity;

/**
 * Created by Administrator on 2016/4/23.
 */
public class User extends BaseEntity {
    private Integer id;
    private String userName;
    private Integer age;



    private Pen pen;

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
    public Pen getPen() {
        return pen;
    }

    public void setPen(Pen pen) {
        this.pen = pen;
    }
}
