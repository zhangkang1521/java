package org.zk.other;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/9/2016.
 */
public class TempTest {
    public static void main(String[] args) throws Exception{
        //System.out.println(com.alibaba.druid.filter.config.ConfigTools.encrypt("xxd_read"));
        String test2 = "RHOXRe4WZzJWyT9ClUolYuueaefw8LFeE8hcXfAxj8HFj14S8pT2ASY9WDkDkehHBfzL0OE/4xt2qws/nxxkGQ==";
        System.out.println(com.alibaba.druid.filter.config.ConfigTools.decrypt(test2));
    }
}
