package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/20/2016.
 */
public class WorkOut {

    public static void main(String[] args) {
        workHard();
        System.out.println("It't nap time.");
    }

    private static void workHard(){
        try{
            workHard();
        } finally {
            workHard();
        }
    }
}
