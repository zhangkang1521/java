package dozer;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.annotations.Test;

/**
 * Created by zhangkang on 2017/7/21.
 */
public class BeanUtilsTest {

    @Test
    public void test1() throws Exception{
        A a = new A();
        a.setUserId(100L);
        B b = new B();
        // 类型不匹配不会报错，自动转换
        BeanUtils.copyProperties(b, a);
        System.out.println(b.getUserId());
    }
}
