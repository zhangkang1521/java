package org.zk.puzzle.character;

/**
 * Created by Administrator on 10/5/2016.
 */
public class StringBufferTest {
    public static void main(String[] args) {
        // 提升为int 调用 StringBuffer(int)
        StringBuffer sb = new StringBuffer('a');
        sb.append("b");
        System.out.println(sb);
    }
}
