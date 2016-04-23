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

//    @Test
//    public void first() {
//        SessionFactory sf = new Configuration().configure().buildSessionFactory();
//        Session session = sf.openSession();
//        session.beginTransaction();
//        User user = new User();
//        user.setUserName("zk");
//        user.setAge(20);
//        session.save(user);
//        session.getTransaction().commit();
//        sf.close();
//    }

    @Test
    public void testLog(){
      //  LOG.error("xxx");
        Logger log = Logger.getLogger("ss");
        System.out.println(log.getClass());
       // System.out.println(LOG.getClass());
    }

}
