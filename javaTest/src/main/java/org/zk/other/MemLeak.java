package org.zk.other;


import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 12/8/2016.
 */
public class MemLeak {
    public static void main(String[] args) throws Exception {
        String str = FileUtils.readFileToString(new File("E:/tmp/test"));
        //str.intern();
    }
}
