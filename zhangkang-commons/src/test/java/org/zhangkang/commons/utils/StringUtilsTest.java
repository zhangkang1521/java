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

    @Test
    public void testBuildSetmethod(){
        long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
            String str = "asdfasdf"+i;
            StringUtils.buildSetMethodName(str);
        }
        System.out.println(System.currentTimeMillis()-start);

//        long start2 = System.currentTimeMillis();
//        for(int i=0; i<1000; i++){
//            StringUtils.buildSetMethodName2("userName");
//        }
//        System.out.println(System.currentTimeMillis()-start2);

    }

    @Test
    public void testFirst2Upcase(){
        Assert.assertEquals("UserName", StringUtils.first2Upcase("UserName"));
        Assert.assertEquals("UserName", StringUtils.first2Upcase("userName"));
    }

    @Test
    public void test(){
        Param param = new Param();
//        param.setAmount("100");
        param.setBankCode("8003");
        param.setOrderId("BILL2016010245234");
        String str = StringUtils.buildUrlparam(param);
        System.out.println(str);
    }
}
