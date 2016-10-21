package org.zk.other.spi;

/**
 * Created by Administrator on 10/17/2016.
 */
public class TextSayHello implements SayHello{
    static {
        System.out.println("TextSayHello initialized!");
    }
    public void sayHello() {
        System.out.println("text say hello!");
    }
}
