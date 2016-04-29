package org.zk.redis.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 邮件订阅
 * Created by zhangkang on 2016/4/29.
 */
public class MailRedisListener implements MessageListener {

    private Logger LOG = LoggerFactory.getLogger(MailRedisListener.class);

    public void onMessage(Message message, byte[] pattern) {
        LOG.debug("[redis subscribe] channel:{},message{}", new String(pattern), message);
    }
}
