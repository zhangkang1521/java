package org.zk.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class Rethrowing {

    public static void f() throws Exception{
        throw new OneException("thrown from f()");
    }

    public static void g() throws Exception{
        try {
            f();
        }catch (Exception e){
            e.printStackTrace();
            //throw e;
            //throw (Exception) e.fillInStackTrace();// 会丢失掉f()方法异常信息，这里便是异常的新发生地
            // 这里重新抛出另外一种异常，main方法中打印的异常链对OneException一无所知，也不知道调用了f()方法
            // 很危险，谨记!!!
            throw new TwoException(e);
        }
    }

    public static void main(String[] args) {
        try{
            g();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
