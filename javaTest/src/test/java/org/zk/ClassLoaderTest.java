package org.zk;

import sun.misc.Launcher;

import java.net.URL;
import java.util.Enumeration;

/**
 * Created by root on 16-6-10.
 */
public class ClassLoaderTest {

    //psvm
    public static void main(String[] args) throws Exception{
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        //
        Enumeration em = systemClassLoader.getResources("");
        while (em.hasMoreElements()){
            System.out.println(em.nextElement());
        }
        ClassLoader extCl = systemClassLoader.getParent();
        System.out.println(extCl);
        System.out.println(extCl.getParent());
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }

}

class A {
    static {
        System.out.println("A load...");
    }
}
