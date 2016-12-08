package apache;

import com.beust.jcommander.internal.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.zhangkang.entity.User;

import java.util.Map;

/**
 * Created by Administrator on 12/8/2016.
 */
public class BeanUtilsTest {

    User user = new User();

    @BeforeTest
    public void testBefore() {
        user.setAge(20);
        user.setUserName("zk");
    }

    @Test
    public void testGetProperty() throws Exception{
        System.out.println(BeanUtils.getProperty(user, "age"));
    }

    @Test
    public void testPopulate() throws Exception{
        Map<String, Object> map = Maps.newHashMap();
        map.put("userName", "zhangkang");
        map.put("age", 12);
        User user1 = new User();
        BeanUtils.populate(user1, map);
    }

    @Test
    public void testClone() throws Exception {
        User user2 = (User)BeanUtils.cloneBean(user);
        System.out.println(System.identityHashCode(user));
        System.out.println(System.identityHashCode(user2));
        System.out.println(user.hashCode());
        System.out.println(user2.hashCode());
    }
}
