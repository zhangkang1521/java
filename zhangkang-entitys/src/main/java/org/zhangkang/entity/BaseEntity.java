package org.zhangkang.entity;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2016/4/4.
 */
public class BaseEntity {
    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }
}
