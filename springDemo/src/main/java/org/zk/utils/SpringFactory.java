package org.zk.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  用于在代码中动态获取bean
 * Created by zhangkang on 2016/5/19.
 */
@Component
public class SpringFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String id){
        // 这个地方能直动转型，不错! spring 的接口是 Object getBean(String)，需要强转
        return (T)applicationContext.getBean(id);
    }
}
