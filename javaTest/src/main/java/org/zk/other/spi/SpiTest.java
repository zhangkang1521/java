package org.zk.other.spi;

import java.util.ServiceLoader;

/**
 * service provider interface
 * 新版jdbc驱动使用了此技术，可以不写Class.forName()
 * Created by Administrator on 10/17/2016.
 */
public class SpiTest {

    public static void main(String[] args) {
        ServiceLoader<SayHello> loaders =
                ServiceLoader.load(SayHello.class);
        for (SayHello in : loaders) {
            in.sayHello();
        }
    }
}
