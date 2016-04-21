package com.autoserve.abc.service.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-29,15:28
 */
public class MacAddressUtilTest {
    @Test
    public void testGetMacAddress() {
        String mac = MacAddressUtil.getMacAddress();
        System.out.println(mac);
        Assert.assertNotNull(mac);
    }
}
