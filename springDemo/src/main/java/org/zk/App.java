package org.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zk.beans.Person;

/**
 * Hello world!
 *
 */
public class App 
{

    private static Log log = LogFactory.getLog(App.class);

    public static void main( String[] args )
    {
        System.out.println(log.getClass());
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
////        Person p1 = (Person)ctx.getBean("person1");
////        Person p1 = ctx.getBean("person1", Person.class);
//       log.error("contains:"+ctx.containsBean("person1"));
//        System.out.println(ctx.isSingleton("person1"));
//        System.out.println(ctx.isPrototype("person1"));
//        System.out.println(ctx.getType("person1"));
//        System.out.println(p1.getUserName());
    }
}
