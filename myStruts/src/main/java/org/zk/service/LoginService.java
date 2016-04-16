package org.zk.service;

import org.zk.entity.User;

/**
 * Created by Administrator on 2016/4/2.
 */
public class LoginService {

    /**
     * 登录
     * @param user
     * @return
     */
    public boolean login(User user){
        if("zk".equals(user.getUserName())&& "123456".equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
