package org.zk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zk.entity.User;
import org.zk.service.AbstractUserService;
import org.zk.service.UserService;

/**
 * Created by Administrator on 9/4/2016.
 */
@Service
public class UserServiceImpl extends AbstractUserService implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Transactional
//    public int save(User user){
//        jdbcTemplate.execute("INSERT INTO tb_user (`USER_NAME`, `AGE`) VALUES('USERNAME', 23)");
//        //hrow new RuntimeException("xx");
//        return 1;
//    }


}
