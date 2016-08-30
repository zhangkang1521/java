package org.zk;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.zk.beans.User;

import java.net.URL;

/**
 * Created by Administrator on 8/27/2016.
 */
public class SpringTest {

    public static void main(String[] args) throws Exception{
        String config = "applicationContext.xml";
//        Resource resource = new ClassPathResource("applicationContext.xml");
//        URL url = resource.getURL();
//        XmlBeanFactory ctx = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        ApplicationContext ctx = new FileSystemXmlApplicationContext("E:\\java_workspace\\java\\springDemo\\src\\main\\resources\\applicationContext.xml");
       // ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        User user = (User)ctx.getBean("user1");
       String[] alias = ctx.getAliases("user");
    }
}
