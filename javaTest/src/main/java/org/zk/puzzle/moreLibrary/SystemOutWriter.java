package org.zk.puzzle.moreLibrary;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 9/21/2016.
 */
public class SystemOutWriter {
    public static void main(String[] args) {
        String str = "hello你好";
        for(int i=0; i<str.length(); i++){
            // PrintStream 缓冲
            System.out.write(str.charAt(i));
            //System.out.flush();
        }

    }
}
