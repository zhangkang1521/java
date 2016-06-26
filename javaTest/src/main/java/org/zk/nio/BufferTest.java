package org.zk.nio;

import java.nio.CharBuffer;

/**
 * Created by root on 16-6-11.
 */
public class BufferTest {

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(8);
        buffer.put('a');
        buffer.put('b');
        buffer.flip(); // flip 将position置为0，用于读取
        System.out.println("after flip:");
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        buffer.clear(); // clear position=0 limit=capacity 为写入数据做准备
        // after clear
        System.out.println("after clear");
        buffer.put('c');

//        buffer.clear();
        System.out.println("=="+buffer.get());
    }


}
