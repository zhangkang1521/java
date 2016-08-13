package org.zk.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class WhoCalled {

    static void f(){
        try{
            throw new Exception();
        }catch (Exception e){
            for(StackTraceElement ste:e.getStackTrace()){
                System.out.println(ste.toString());//打印每个异常frame
            }
        }
    }

    static void g(){
        f();
    }

    static void h(){
        g();
    }

    public static void main(String[] args) {
        f();
        System.out.println("=================");
        g();
        System.out.println("===============");
        h();
    }
}
