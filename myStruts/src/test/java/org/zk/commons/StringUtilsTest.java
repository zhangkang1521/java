package org.zk.commons;

import org.junit.Test;

/**
 * Created by Administrator on 2016/4/3.
 */
public class StringUtilsTest {

    @Test
    public void test1(){
        String path = StringUtils.removeContextPath("/xxx", "/xxx/login.action");
        System.out.println(path);
    }

    @Test
    public void test2(){
        String path = StringUtils.removeContextPath("", "/login.action");
        System.out.println(path);
    }
}
