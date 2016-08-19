package org.zk.thinking.exception;

/**
 * Created by Administrator on 8/14/2016.
 */
public class CatchException {

    public static void f(){
        try{
            throw new RuntimeException("aa");
        }catch (ArithmeticException e){
            throw e;
        }
    }

    public static void main(String[] args) {
        f();
    }
}
