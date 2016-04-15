package org.zk.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.zk.entity.User;
import org.zk.entity.User2;

import java.util.Arrays;

/**
 * Created by zhangkang on 2016/4/15.
 */
public class JsonSerializeTest {

    @Test
    public void test1() {
        User user = new User();
        user.setAge(23);
        user.setUserName("zk");
        // , SerializerFeature.WriteClassName
        byte[] bytes = JSON.toJSONBytes(user, SerializerFeature.WriteClassName);

        User user2 = (User)JSON.parse(bytes);
        System.out.println(user2);
    }
}
