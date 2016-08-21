package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/20/2016.
 */
public class ExceptionallyUnsafe {

    public static void sneakyThrow(Throwable t){
        Thread.currentThread().stop(t);
    }
}
