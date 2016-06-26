package org.zk;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by root on 16-6-5.
 */
public class CommonsCodecTest {

    @Test
    public void test1(){
        System.out.println(DigestUtils.md5Hex("123456"));
        byte[] bytes = DigestUtils.md5("123456");
        System.out.println(Hex.encodeHexString(bytes));
    }
}
