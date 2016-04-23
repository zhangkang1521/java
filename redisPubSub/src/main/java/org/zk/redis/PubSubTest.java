package org.zk.redis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by Administrator on 2016/4/16.
 */
public class PubSubTest extends JedisPubSub {

    public void onMessage(String s, String s1) {

    }

    public void onPMessage(String s, String s1, String s2) {

    }

    public void onSubscribe(String s, int i) {
        System.out.println("subscribe:"+s+i);
    }

    public void onUnsubscribe(String s, int i) {

    }

    public void onPUnsubscribe(String s, int i) {

    }

    public void onPSubscribe(String s, int i) {

    }

    public static  void main(String[] args) {
        Jedis jedis = new Jedis("192.168.0.100", 6379);
//        Client client = jedis.getClient();
        jedis.set("aa", "AA");
//        PubSubTest pubSubTest = new PubSubTest();
//        pubSubTest.proceed(client, "news.share", "news.blog");
        jedis.close();
        System.out.println("over");
    }
}
