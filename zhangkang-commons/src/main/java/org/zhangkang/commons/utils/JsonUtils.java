package org.zhangkang.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by root on 16-6-5.
 */
public class JsonUtils {

    public List parseList(String jsonStr, Class cls){
        List list = Lists.newArrayList();
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        for(Object jsonObject:jsonArray){
            Object result = JSON.parseObject(jsonObject.toString(), cls);
            list.add(result);
        }
        return list;
    }
}
