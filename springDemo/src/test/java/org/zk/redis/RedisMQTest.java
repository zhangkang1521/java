package org.zk.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.zk.BaseTest;

/**
 * Created by zhangkang on 2016/4/29.
 */
public class RedisMQTest extends BaseTest {

    @Autowired
    RedisMQ redisMQ;

    @Test
    public void testPublish() {
        for (int i = 0; i < 1; i++) {
            redisMQ.publish("mail", "send mail "+i);
            redisMQ.publish("sms", "send message "+i);
        }
    }
}
