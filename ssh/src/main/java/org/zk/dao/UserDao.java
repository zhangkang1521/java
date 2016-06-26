package org.zk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zk.entity.User;

/**
 * Created by root on 6/26/16.
 */
@Repository
public class UserDao {
    @Autowired
    private SessionFactory sf;

    @Transactional
    public void save(User user) {
        Session session = sf.getCurrentSession();
        session.save(user);
    }
}
