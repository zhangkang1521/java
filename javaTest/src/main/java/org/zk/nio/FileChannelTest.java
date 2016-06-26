package org.zk.nio;

import sun.nio.cs.ext.DoubleByte;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by root on 16-6-11.
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception{
        FileChannel inFileChannel = new FileInputStream("/home/zk/test/test1").getChannel();
        FileChannel outFileChannel = new FileInputStream("/home/zk/test/test2").getChannel();
        ByteBuffer byteBuffer = inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
        outFileChannel.write(byteBuffer);
//        Charset charset = Charset.forName("UTF-8");
//        CharsetDecoder decoder = charset.newDecoder();
//        CharBuffer charBuffer = decoder.decode(byteBuffer);
//        System.out.println(charBuffer);
    }
}
