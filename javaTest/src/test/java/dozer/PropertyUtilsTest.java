package dozer;

import org.apache.commons.beanutils.PropertyUtils;
import org.testng.annotations.Test;

/**
 * Created by zhangkang on 2017/7/21.
 */
public class PropertyUtilsTest {

    @Test
    public void test1() throws Exception{
        // 类型不匹配会报错
        A a = new A();
        a.setUserId(100L);
        B b = new B();
        PropertyUtils.copyProperties(b ,a);
        System.out.println(b.getUserId());
    }
}
