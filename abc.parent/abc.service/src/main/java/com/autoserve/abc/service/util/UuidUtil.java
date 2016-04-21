package com.autoserve.abc.service.util;


import java.util.UUID;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-29,15:02
 */
public class UuidUtil {
    public static String generateUuid() {
    	//随机产生一个[0,10)的数字
    	int random=(int)(Math.random()*10);
        return UUID.randomUUID().toString().replaceAll("-", String.valueOf(random));
    }
}
