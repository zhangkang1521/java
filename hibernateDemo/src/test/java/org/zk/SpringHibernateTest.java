package org.zk;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.SourceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zk.entity.User;

/**
 * Created by root on 16-5-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringHibernateTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    SessionFactory sf;

//    Log log = LogFactory.getLog(SpringHibernateTest.class);

    @Test
    public void test1(){
        sf.openSession().beginTransaction();

        sf.getCurrentSession().getTransaction().commit();
        System.out.println("ss");
    }
}
