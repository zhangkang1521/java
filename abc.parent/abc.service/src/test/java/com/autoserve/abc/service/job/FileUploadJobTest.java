package com.autoserve.abc.service.job;

import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;

/**
 * @author yuqing.zheng Created on 2014-11-28,15:04
 */
public class FileUploadJobTest extends BaseServiceTest {
    @Autowired
    private FileUploadJob fileUploadJob;

    @Test
    public void test() {
        DateTime dt = new DateTime();
        dt.minusDays(1);
        System.out.println(dt.toString("yyyy-MM-dd"));
    }

    @Test
    public void testJobExist() {
        try {
            Method method = FileUploadJob.class.getDeclaredMethod("jobExist");
            method.setAccessible(true);
            boolean res = (Boolean) method.invoke(fileUploadJob);
            Assert.assertEquals(res, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
