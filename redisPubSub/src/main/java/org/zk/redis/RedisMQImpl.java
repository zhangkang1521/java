package org.zk.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis消息发布
 * Created by zhangkang on 2016/4/29.
 */
public class RedisMQImpl implements RedisMQ {

    private RedisTemplate<String, Object> redisTemplate;

    private Logger LOG = LoggerFactory.getLogger(getClass());

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, String message) {
        LOG.debug("[redis publish] channel:{},message:{}", channel, message);
        redisTemplate.convertAndSend(channel, message);
    }
}
