/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved.
 *
 */
package com.xxdai.zk;

import com.xxdai.concurrent.DistributedLock;
import com.xxdai.session.ZooKeeperHelper;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * ZookeeperFactoryBean
 *
 * @version $Id: ZookeeperFactoryBean.java 2015/2/26 13:40 $
 * @since jdk1.6
 */
public class ZookeeperFactoryBean implements FactoryBean<CuratorFramework>, InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CuratorFramework zkClient;

    //设置ZK链接串
    public void setZkConnectionString(String zkConnectionString) {
        this.zkConnectionString = zkConnectionString;
    }

    private String zkConnectionString;

    public CuratorFramework getObject() {
        return zkClient;
    }

    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        zkClient.close();
    }

    //创建ZK链接
    public void afterPropertiesSet() {
        //1000 是重试间隔时间基数，3 是重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkClient = createWithOptions(zkConnectionString, retryPolicy, 3000, 10000);
        registerListeners(zkClient);
        zkClient.start();
    }


    /**
     * 通过自定义参数创建
     */
    public CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }

    //注册需要监听的监听者对像.
    private void registerListeners(CuratorFramework client) {
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                logger.info("CuratorFramework state changed: {}", newState);
                if (newState == ConnectionState.CONNECTED) {
                    EnsurePath lockPath = new EnsurePath(DistributedLock.ROOT_LOCK);
                    try {
                        lockPath.ensure(client.getZookeeperClient());
                    } catch (Exception e) {
                        logger.error("DistributedLock.ROOT_LOCK EnsurePath failed");
                    }

                    EnsurePath sessionPath = new EnsurePath(ZooKeeperHelper.GROUP_NAME);
                    try {
                        sessionPath.ensure(client.getZookeeperClient());
                    } catch (Exception e) {
                        logger.error("ZooKeeperHelper.GROUP_NAME EnsurePath failed");
                    }
                }
            }
        });

        client.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
            @Override
            public void unhandledError(String message, Throwable e) {
                logger.info("CuratorFramework unhandledError: {}", message);
            }
        });
    }
}
