package org.zhangkang.commons.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2016/4/23.
 */
public class StringUtilsTest {

    @Test
    public void testBuildSetMethod(){
        Assert.assertEquals("setUserName", StringUtils.buildSetMethodName("userName"));
        Assert.assertEquals("setA",StringUtils.buildSetMethodName("a"));
        try {
            StringUtils.buildSetMethodName(null);
        }catch (Exception e){
            Assert.assertEquals(e.getClass(), IllegalArgumentException.class);
        }
        try {
            StringUtils.buildSetMethodName("");
        }catch (Exception e){
            Assert.assertEquals(e.getClass(), IllegalArgumentException.class);
        }
    }
}
