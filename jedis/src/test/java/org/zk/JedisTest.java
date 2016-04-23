package org.zk;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2016/4/16.
 */
public class JedisTest {

    @Test
    public void testString(){
        Jedis jedis = new Jedis("192.168.0.100", 6379);
        jedis.set("userName", "zhangkang");
        jedis.select(1);
        String userName = jedis.get("userName");
        System.out.println(userName);
    }
}
