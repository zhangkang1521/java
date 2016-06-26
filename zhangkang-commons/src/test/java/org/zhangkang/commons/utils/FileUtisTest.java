package org.zhangkang.commons.utils;

import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

import java.io.File;
import java.util.Arrays;

/**
 * Created by root on 16-6-10.
 */
public class FileUtisTest {

    @Test
    public void testFile2Bytes(){
        byte[] bytes = FileUtils.file2Bytes(new File("/home/zk/tmp.txt"));
        System.out.println(Arrays.toString(bytes));
    }

}
