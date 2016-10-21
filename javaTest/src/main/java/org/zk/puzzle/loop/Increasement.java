package org.zk.puzzle.loop;

/**
 * Created by Administrator on 9/26/2016.
 */
public class Increasement {
    public static void main(String[] args) {
        int j = 0;
        // int tmp = j
        // j = j + 1
        // j = tmp
        j = j++;
        System.out.println(j);
    }
}
