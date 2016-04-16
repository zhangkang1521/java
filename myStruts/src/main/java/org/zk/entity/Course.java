package org.zk.entity;

import org.zhangkang.commons.BaseEntity;

/**
 * Created by Administrator on 2016/4/4.
 */
public class Course extends BaseEntity {
    private String no;
    private String name;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
