package org.zhangkang.commons;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2016/4/4.
 */
public class BaseEntity {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
