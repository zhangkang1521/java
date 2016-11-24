package org.zk;

import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.zk.entity.User;

/**
 * Created by zhangkang on 2016/4/22.
 */
public class HibernateTest {

   // private static final CoreMessageLogger LOG = Logger.getMessageLogger( CoreMessageLogger.class, HibernateTest.class.getName() );

    @Test
    public void testSave() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User();
        //user.setId(10);
        user.setUserName("zk");
        session.save(user);
        session.getTransaction().commit();
        sf.close();
    }

    @Test
    public void testMerge() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User();
        // 不提供id ,insert
        // 提供id, 找到update,找不到insert
        user.setId(19);
        user.setUserName("zy");
        session.merge(user);
        session.getTransaction().commit();
        sf.close();
    }


    @Test
    public void first() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        User user = (User)session.get(User.class, 1);
        user.setAge(12);
        session.evict(user);
        session.getTransaction().commit();
        sf.close();
    }

    @Test
    public void testLog(){
      //  LOG.error("xxx");
        Logger log = Logger.getLogger("ss");
        System.out.println(log.getClass());
       // System.out.println(LOG.getClass());
    }

}
