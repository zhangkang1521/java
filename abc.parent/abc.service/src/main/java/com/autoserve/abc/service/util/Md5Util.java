package com.autoserve.abc.service.util;

import java.security.MessageDigest;

public class Md5Util {
	public final static String md5(String plainText) {
		   // 返回字符串
		   String md5Str = null;
		   try {
		    // 操作字符串
		    StringBuffer buf = new StringBuffer();
		  
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(plainText.getBytes());
		    // 计算出摘要,完成哈希计算。
		    byte b[] = md.digest();
		    int i;
		    for (int offset = 0; offset < b.length; offset++) {
		     i = b[offset];
		     if (i < 0) {
		      i += 256;
		     }
		     if (i < 16) {
		      buf.append("0");
		     }
		     // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
		     buf.append(Integer.toHexString(i));
		    }
		    // 32位的加密
		   // md5Str = buf.toString();
		    // 16位的加密
		//    md5Str = buf.toString().md5Strstring(8,24);
		    md5Str = buf.toString().substring(8,24);
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		   return md5Str;
		}
}
