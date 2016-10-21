package org.zk.other;

import java.nio.charset.Charset;

/**
 * Hello world!
 */
public class App {

    public int id = 1;
    public void test(){
        id++;
        System.out.println("test");
    }
    public static void main(String[] args) {
//        for(int i=0; i<10; i++){
//            GcTest gcTest = new GcTest();
//            WeakReference weakReference = new WeakReference(gcTest);
//            gcTest = null;
//        }
//        System.gc();

//        String str = new String("test");
//        WeakReference weakReference = new WeakReference(str);
//        str = null;
//        System.out.println(weakReference.get());
//        System.gc();// 弱引用执行gc时一定回收
//        System.out.println(weakReference.get());
        System.out.println(Charset.defaultCharset());
    }


}
