package org.zk.puzzle.character;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 10/5/2016.
 */
public class CharsetTest {
    public static void main(String[] args) throws Exception{
        System.out.println(Charset.defaultCharset());
        byte bytes[] = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte)i;
        }
        // new String最好指定字符集，否则在不同的机器上，执行结果不一致
        //String str = new String(bytes);
        String str = new String(bytes, "ISO-8859-1");

        for (int i = 0; i < str.length(); i++) {
            System.out.print((int)str.charAt(i) + " ");
        }
    }
}
