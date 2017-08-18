package org.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import org.testng.annotations.Test;
import org.zhangkang.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 16-6-5.
 */
public class FastjsonTest {

    @Test
    public void test1() {
        User user = new User();
        user.setUserName("zk");
        user.setId(100);
        user.setAge(20);
        user.setBirthDay(new Date());
        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(user, SerializerFeature.IgnoreNonFieldGetter));
//        System.out.println(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void test2() {
        String str = "{\"age\":20,\"birthDay\":\"1465118037946\",\"id\":100,\"userName\":\"zk\"}";
        User user = JSON.parseObject(str, User.class);
        System.out.println(user);
    }

    @Test
    public void test3() {
        String str = "[{\"age\":20,\"id\":0,\"userName\":\"zk0\"},{\"age\":21,\"id\":1,\"userName\":\"zk1\"},{\"age\":22,\"id\":2,\"userName\":\"zk2\"}]";
//        List list = JSON.parseObject(str, new TypeReference<ArrayList>(){});
        parseList(str, User.class);
    }

    public List parseList(String str, Class cls){
        List list = Lists.newArrayList();
        JSONArray jsonArray = JSON.parseArray(str);
        for(Object jsonObject:jsonArray){
            Object result = JSON.parseObject(jsonObject.toString(), cls);
            list.add(result);
        }
        return list;
    }
}

