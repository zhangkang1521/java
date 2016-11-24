package org.zk.other;

import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/9/2016.
 */
public class TempTest {
    public static void main(String[] args) throws Exception{
        //System.out.println(com.alibaba.druid.filter.config.ConfigTools.encrypt("xxd_read"));
        String test2 = "NG02OkgboAnxTmuzir+HYC5vR9nUGVIFWsQ3dpSjUqsDSzA7qnTUBLbHU3RN5DkGzMg+MdEkD4z58JbxKVHyTw==";
        System.out.println(com.alibaba.druid.filter.config.ConfigTools.decrypt(test2));
    }
}
