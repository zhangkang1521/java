package org.zk.thinking.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class TwoException extends Exception {

    public TwoException(String s){
        super(s);
    }

    public TwoException(Throwable t){
        super(t);
    }
}
