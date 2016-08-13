package org.zk.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class LostMessage {

    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void g() throws OneException {
        throw new OneException("ss");
    }

    public static void main(String[] args) {
        try{
            LostMessage lostMessage = new LostMessage();
            try{
                lostMessage.f();
            } finally {
                lostMessage.g();
            }

        }catch (Exception e){
            e.printStackTrace();// my godder!!! VeryImportantException竟然没有打印出来，被TwoException覆盖了！！！
        }
    }
}
