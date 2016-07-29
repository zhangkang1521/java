/**
 * Copyright (c) 2014, www.xxdai.com All Rights Reserved.
 */
package com.xxdai.session;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ZooKeeperHelper
 *
 * @version $Id: ZooKeeperHelper.java 20761 2015-06-30 05:49:29Z xiaoying $
 * @since jdk1.6
 */
public class ZooKeeperHelper {
    /**
     * 日志
     */
    private static Logger log = Logger.getLogger(ZooKeeperHelper.class);
    public static final String GROUP_NAME = "/SESSIONS";


    /**
     * 验证指定ID的节点是否存在
     *
     * @param id
     * @param zk
     * @return
     */
    public static boolean isSessionExists(String id, CuratorFramework zk) {
        if (zk != null) {
            //获取元数据
            SessionMetaData metadata = getSessionMetaData(id, zk);
            //如果不存在或是无效，则直接返回null
            if (metadata != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证指定ID的节点是否有效
     *
     * @param id
     * @param zk
     * @return
     */
    public static boolean isSessionValid(String id, CuratorFramework zk) {
        if (zk != null) {
            //获取元数据
            SessionMetaData metadata = getSessionMetaData(id, zk);
            //如果不存在或是无效，则直接返回null
            if (metadata != null) {
                return isSessionMetaValidate(metadata);
            }
        }
        return false;
    }


    /**
     * 验证Session数据是否有效
     *
     * @return
     */
    private static boolean isSessionMetaValidate(SessionMetaData metadata) {
        if (metadata == null)
            return false;

        Long now = System.currentTimeMillis();//当前时间
        //检查是否过期
        Long timeout = metadata.getLastAccessTm() + metadata.getMaxIdle();//空闲时间
//        log.debug(String.format("checkSessionMetaTimeout idle: %d, metadata.getLastAccessTm: %s now: %s, timeout: %s",
//                metadata.getMaxIdle(),
//                DateUtil.format(metadata.getLastAccessTm(), DateUtil.FullDateFormat),
//                DateUtil.format(now, DateUtil.FullDateFormat),
//                DateUtil.format(timeout, DateUtil.FullDateFormat)));
        metadata.setValidate(now < timeout);
        return metadata.getValidate();
    }

    /**
     * 返回指定ID的Session元数据
     *
     * @param id
     * @return
     */
    public static SessionMetaData getSessionMetaData(String id, CuratorFramework zk) {
        if (zk != null) {
            String path = GROUP_NAME + "/" + id;
            try {
                //检查节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                //stat为null表示无此节点
                if (stat == null) {
                    return null;
                }
                //获取节点上的数据
                byte[] data = zk.getData().forPath(path);
                if (data != null) {
                    //反序列化
                    Object obj = SerializationUtils.deserialize(data);
                    //转换类型
                    if (obj instanceof SessionMetaData) {
                        SessionMetaData metadata = (SessionMetaData) obj;
                        //设置当前版本号
                        metadata.setVersion(stat.getVersion());
                        //log.debug("metadata: "+metadata.getId()+",getVersion:"+metadata.getVersion()+",getValidate:"+metadata.getValidate());
                        return metadata;
                    }
                }
            } catch (Exception e) {
                log.error("getSessionMetaData: " + e.getMessage());
            }
        }
        return null;
    }


    /**
     * 更新Session节点的元数据
     *
     * @param id
     * @param zk
     */
    public static void updateSessionMetaData(String id, CuratorFramework zk) {
        updateSessionMetaData(getSessionMetaData(id, zk), zk);
    }

    /**
     * 更新Session节点的元数据
     *
     * @param metadata
     * @param zk
     */
    private static void updateSessionMetaData(SessionMetaData metadata, CuratorFramework zk) {
        try {
            if (metadata != null) {
                String id = metadata.getId();

                if (!isSessionMetaValidate(metadata))
                    removeSessionAttributes(id, zk);

                //设置最后一次访问时间
                metadata.setLastAccessTm(System.currentTimeMillis());
                //更新节点数据
                String path = GROUP_NAME + "/" + id;
                byte[] data = SerializationUtils.serialize((Serializable) metadata);
                zk.setData().withVersion(metadata.getVersion()).forPath(path, data);
                //log.debug("更新Session节点的元数据完成[" + path + "]");
            }
        } catch (KeeperException e) {
            if ("BADVERSION".equals(e.code().name()))
                log.warn(e.getMessage());
            else
                log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 创建指定Session ID的节点
     *
     * @param metadata
     * @return
     */
    public static String createSessionNode(SessionMetaData metadata, CuratorFramework zk) {
        if (metadata == null) {
            return null;
        }

        if (zk != null) {
            String path = GROUP_NAME + "/" + metadata.getId();
            log.debug("createSessionNode:" + path);
            try {
                //检查节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                //stat为null表示无此节点，需要创建
                if (stat == null) {
                    //创建组件点
                    String createPath = zk.create().withMode(CreateMode.PERSISTENT).forPath(path);
                    //log.debug("创建Session节点完成:[" + createPath + "]");
                    //写入节点数据
                    zk.setData().forPath(path, SerializationUtils.serialize((Serializable) metadata));
                    return createPath;
                }
            } catch (Exception e) {
                log.error("createSessionNode:" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * 删除指定Session ID的节点
     *
     * @param sid Session ID
     * @return
     */
    public static boolean deleteSessionNode(String sid, CuratorFramework zk) {
        if (zk != null) {
            String path = GROUP_NAME + "/" + sid;
            try {
                //检查节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                //如果节点存在则删除之
                if (stat != null) {
                    //先删除子节点
//                    List<String> nodes = zk.getChildren().forPath(path);
//                    if (nodes != null) {
//                        for (String node : nodes) {
//                            zk.delete().forPath(path + "/" + node);
//                        }
//                    }
                    //删除父节点
//                    zk.delete().forPath(path);
                    zk.delete().deletingChildrenIfNeeded().forPath(path);
                    log.debug("SessionNode deleted:[" + path + "]");
                    return true;
                }
            } catch (Exception e) {
                log.warn("deleteSessionNode:" + e.getMessage());
            }
        }
        return false;
    }


    /**
     * 在指定Session ID的节点下添加数据节点
     *
     * @param sid   Session ID
     * @param name  数据节点的名称
     * @param value 数据
     * @return
     */
    public static boolean setSessionData(String sid, String name, Object value, CuratorFramework zk) {
        boolean result = false;
        if (zk != null) {
            String path = GROUP_NAME + "/" + sid;
            try {
                //检查指定的Session节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                //如果节点存在则删除之
                if (stat != null) {
                    //查找数据节点是否存在，不存在就创建一个
                    String dataPath = path + "/" + name;
                    stat = zk.checkExists().forPath(dataPath);
                    if (stat == null) {
                        //创建数据节点
                        zk.create().withMode(CreateMode.PERSISTENT).forPath(dataPath);
                        //log.debug("创建数据节点完成[" + dataPath + "]");
                    }
                    //在节点上设置数据，所有数据必须可序列化
                    if (value instanceof Serializable) {
                        int dataNodeVer = -1;
                        if (stat != null) {
                            //记录数据节点的版本
                            dataNodeVer = stat.getVersion();
                        }
                        byte[] data = SerializationUtils.serialize((Serializable) value);
                        stat = zk.setData().withVersion(dataNodeVer).forPath(dataPath, data);
                        // log.debug("更新数据节点数据完成[" + dataPath + "][" + value +"]");
                        result = true;
                    }
                }

            } catch (Exception e) {
                log.error("setSessionData:" + e.getMessage());
            }
        }
        return result;
    }


    /**
     * 返回指定Session ID的节点下数据
     *
     * @param sid  Session ID
     * @param name 数据节点的名称
     * @return
     */
    public static Object getSessionData(String sid, String name, CuratorFramework zk) {
        if (zk != null) {
            String path = GROUP_NAME + "/" + sid;
            try {
                //检查指定的Session节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                if (stat != null) {
                    //查找数据节点是否存在
                    String dataPath = path + "/" + name;
                    stat = zk.checkExists().forPath(dataPath);
                    Object obj = null;
                    if (stat != null) {
                        //获取节点数据
                        byte[] data = zk.getData().forPath(dataPath);
                        if (data != null) {
                            //反序列化
                            obj = SerializationUtils.deserialize(data);
                        }
                    }
                    return obj;
                }

            } catch (Exception e) {
                log.error("getSessionData:" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * 删除指定Session ID的节点下数据
     *
     * @param sid  Session ID
     * @param name 数据节点的名称
     * @return
     */
    public static void removeSessionData(String sid, String name, CuratorFramework zk) {
        if (zk != null) {
            String path = GROUP_NAME + "/" + sid;
            try {
                //检查指定的Session节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                if (stat != null) {
                    //查找数据节点是否存在
                    String dataPath = path + "/" + name;
                    stat = zk.checkExists().forPath(dataPath);
                    if (stat != null) {
                        //删除节点
                        zk.delete().forPath(dataPath);
                    }
                }

            } catch (Exception e) {
                log.warn("removeSessionData:" + e.getMessage());
            }
        }
    }


    /**
     * 删除指定Session ID下所有的节点数据
     *
     * @param sid Session ID
     * @return
     */
    private static void removeSessionAttributes(String sid, CuratorFramework zk) {
        if (zk != null) {
            String path = GROUP_NAME + "/" + sid;
            try {
                //检查指定的Session节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                if (stat != null) {
                    //删除所有子节点
                    List<String> nodes = zk.getChildren().forPath(path);
                    if (nodes != null) {
                        for (String node : nodes) {
                            //查找数据节点是否存在
                            String dataPath = path + "/" + node;
                            stat = zk.checkExists().forPath(dataPath);
                            if (stat != null) {
                                //删除
                                zk.delete().deletingChildrenIfNeeded().forPath(dataPath);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                log.warn("removeSessionAttributes:" + e.getMessage());
            }
        }
    }

    /**
     * 删除过期节点
     *
     * @return
     */
    public synchronized static int deleteInvalidSessionNode(CuratorFramework zk) {
        int count = 0;
        if (zk != null) {
            String path = GROUP_NAME;
            try {
                //检查节点是否存在
                Stat stat = zk.checkExists().forPath(path);
                if (stat == null)
                    return count;

                //取出节点
                long startTime = new Date().getTime();
                List<String> nodes = zk.getChildren().forPath(path);
                long getChildrenTime = new Date().getTime();
                log.info("list sessions cost times: " + (getChildrenTime - startTime));
                if (nodes == null)
                    return count;
                log.info("session nodes count: " + nodes.size());

                for (String node : nodes) {
                    //查找数据节点是否存在
                    String dataPath = path + "/" + node;
                    stat = zk.checkExists().forPath(dataPath);
                    if ((stat == null) || isSessionValid(node, zk))
                        continue;

                    try {
                        //删除过期节点
                        zk.delete().deletingChildrenIfNeeded().forPath(dataPath);
                        count++;
                    } catch (Exception ne) {
                        log.error(ne.getMessage());
                    }
                }
                log.info(String.format("SessionNode deleted, count: %d, times: %d", count, new Date().getTime() - getChildrenTime));
            } catch (Exception e) {
                log.error("deleteInvalidSessionNode:" + e.getMessage());
            }
        }
        return count;
    }
}
