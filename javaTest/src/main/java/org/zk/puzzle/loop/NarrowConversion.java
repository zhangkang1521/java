package org.zk.puzzle.loop;

/**
 * Created by Administrator on 10/2/2016.
 */
public class NarrowConversion {
    public static void main(String[] args) {
        // i 先提升为int，左边全部补符号位1
        // 无符号右移一位 0x7fffffff
        // 窄转换为short 0xffff
        //TODO *= /= += <<= 都会进行 silently narrowing primitive conversion 谨慎
        short i = -1;
        System.out.println(Integer.toHexString(i));
        while(i != 0){
            i >>>= 1; // 自动进行 narrowing primitive conversion
        }
    }
}
