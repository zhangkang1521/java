package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/19/2016.
 */
public class Indecision {

    static boolean decision(int i){
        try {
            return true;
        }finally {
            // finally 里面不要 return break continue throw
            if(i==-1){
                throw new RuntimeException("xx");
            }
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(decision(-1));
    }
}
