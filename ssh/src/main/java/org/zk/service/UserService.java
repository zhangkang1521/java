package org.zk.service;

import org.springframework.stereotype.Service;
import org.zk.entity.User;

/**
 * Created by root on 6/26/16.
 */
public interface UserService {
    int save(User user);
}
