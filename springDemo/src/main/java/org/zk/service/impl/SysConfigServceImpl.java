package org.zk.service.impl;

import org.springframework.stereotype.Service;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Service
public class SysConfigServceImpl {

    public Object getConfig(String key){
        return "sys+"+key;
    }
}
