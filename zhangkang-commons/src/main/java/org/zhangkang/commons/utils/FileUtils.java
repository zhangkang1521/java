package org.zhangkang.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by root on 16-6-10.
 */
public class FileUtils {

    /**
     * 将文件读入字节数组中
     *
     * @param file
     * @return
     */
    public static byte[] file2Bytes(File file) {
        byte[] bytes = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }
}

