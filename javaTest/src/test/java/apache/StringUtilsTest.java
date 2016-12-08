package apache;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

/**
 * Created by Administrator on 12/6/2016.
 */
public class StringUtilsTest {

    @Test
    public void testEquals() {
        System.out.println(StringUtils.equals("11", new String("11")));
    }

    @Test
    public void testJoin() {
        // 字符串拼接
        System.out.println(StringUtils.join(Lists.newArrayList("1", "2", "3"), ","));
    }

    @Test
    public void testCapitalize() {
        // 首字母大写
        System.out.println(StringUtils.capitalize("abcAB"));
        System.out.println(StringUtils.capitalize("Abc"));
    }
}
