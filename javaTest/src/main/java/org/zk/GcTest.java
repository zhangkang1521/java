package org.zk;

/**
 * Created by root on 16-5-28.
 */
public class GcTest {
    private static int seq = 0;
    final private  int ID=seq++;

    public void finalize(){
        System.out.println("invoke finalize : "+ID);
    }
}
