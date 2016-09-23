package org.zk.puzzle.loop;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * Created by Administrator on 9/23/2016.
 */
public class Promotion {

    public static final byte VALUE = (byte) 0x90;

    public static void main(String[] args) {

        for (byte i = Byte.MAX_VALUE; i > Byte.MIN_VALUE; i--) {
            // 这里有个自动类型提升，将byte i提升为int，然后比较
            // -122 != 132
            // 这样比较有自动类型提升，容易让人疑惑，而且出现magic number
            if (i == 0x90) {
                System.out.println("ok");
            }
            // 这样做比较好
            if (i == VALUE) {
                System.out.println("constant ok");
            }
        }
        System.out.println((byte) 0x90); // -122

    }
}
