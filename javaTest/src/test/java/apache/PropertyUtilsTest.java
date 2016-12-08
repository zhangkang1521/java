package apache;

import org.apache.commons.beanutils.PropertyUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.zhangkang.entity.User;

import java.beans.PropertyDescriptor;

/**
 * Created by Administrator on 12/8/2016.
 */
public class PropertyUtilsTest {

    User user = new User();

    @BeforeTest
    public void testBefore() {
        user.setAge(20);
        user.setUserName("zk");
    }

    @Test
    public void testGetProperty() throws Exception{
        Object age = PropertyUtils.getProperty(user, "age");
        PropertyUtils.setProperty(user, "age", 30);
    }

    @Test
    public void testIsReadable() {
        System.out.println(PropertyUtils.isReadable(user, "age"));
        System.out.println(PropertyUtils.isReadable(user, "age2"));
    }

    @Test
    public void copy() throws Exception{
        User user2 = new User();
        PropertyUtils.copyProperties(user2, user);
    }

    @Test
    public void testProptyType() throws Exception{
        System.out.println(PropertyUtils.getPropertyType(user, "age"));
        System.out.println(PropertyUtils.getPropertyType(user, "userName"));
    }
}
