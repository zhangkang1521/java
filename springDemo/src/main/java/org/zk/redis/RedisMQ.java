package org.zk.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhangkang on 2016/4/29.
 */
public interface RedisMQ {
    /**
     * 发布消息
     * @param channel 频道
     * @param message 消息内容
     */
    void publish(String channel, String message);

}
