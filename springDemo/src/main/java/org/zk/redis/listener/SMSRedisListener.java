package org.zk.redis.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.Serializable;

/**
 * 短信发送监听
 * Created by zhangkang on 2016/4/29.
 */
public class SMSRedisListener implements MessageListener {

    private Logger LOG = LoggerFactory.getLogger(SMSRedisListener.class);

    public void onMessage(Message message, byte[] pattern) {
       LOG.debug("[redis subscribe] channel:{},message{}", pattern, message);
    }

//    public void handleMessage(Serializable message){
//        System.out.println(message);
//    }
}
