package org.zk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zk.entity.User;

/**
 * Created by zhangkang on 2016/6/24.
 */
@Repository
public class UserDao {

    @Autowired
    SessionFactory sf;

    //@Transactional
    public void save(User user){
        Session session = sf.openSession();
        session.save(user);
       // throw new RuntimeException("ss");
    }

    @Transactional
    public User findById(int id){
        Session session = sf.openSession();
        return  (User)session.get(User.class , 1);
    }
}
