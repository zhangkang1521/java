package dozer;

import com.beust.jcommander.internal.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by zhangkang on 2017/7/21.
 */
public class DozerMapperTest {

    Mapper mapper;

    @BeforeTest
    public void before() {
//        mapper = new DozerBeanMapper();
        mapper = new DozerBeanMapper(Lists.newArrayList("dozerMappers.xml"));
    }


    @Test
    public void testType() {
        // 类型不同也可以赋值
        A a = new A();
        a.setUserId(100L);
        B b = mapper.map(a, B.class);
        System.out.println(b.getUserId());
    }

    @Test
    public void testType2() {
        // 类型不同也可以赋值，不兼容会报错
        B b = new B();
        b.setUserId("111");
        A a = mapper.map(b, A.class);
        System.out.println(a.getUserId());
    }

    @Test
    public void test2() {
        // a中为空的不想map给b,怎么做？ xml配置 map-null=false
        A a = new A();
        B b = new B();
        b.setUserId("100");
        mapper.map(a, b);
        System.out.println(b.getUserId());
    }

    @Test
    public void testUnMatch() {
        // a.username -> b.userName
        A a = new A();
        a.setUsername("zk");
        B b = mapper.map(a, B.class);
        System.out.println(b.getUserName());
    }

    @Test
    public void testUnMatch2() {
        // b.userName -> a.username
        B b = new B();
        b.setUserName("zk");
        A a = mapper.map(b, A.class);
        System.out.println(a.getUsername());
    }
}
