package org.zk.service;

import org.zk.entity.User;

/**
 * 注册service
 */
public class RegisterService {

    /**
     * 注册
     * @param user
     * @return
     */
    public boolean register(User user){
        if("zk".equals(user.getUserName())){
            return true;
        } else {
            return false;
        }
    }
}
