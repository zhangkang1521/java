package org.zhangkang.entity;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Pen extends BaseEntity {
    private String name;
    private Integer length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
