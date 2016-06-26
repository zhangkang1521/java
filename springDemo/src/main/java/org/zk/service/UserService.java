package org.zk.service;

import org.zk.beans.User;

/**
 * Created by zhangkang on 2016/4/27.
 */
public interface UserService {

    User findById(Integer id);
    void testLock(String id);
}
