package org.zk.other.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://mp.weixin.qq.com/s/m2XfI2A2jJqFLAI_iNZI-g
 * 多线程下hashMap扩容出现死循环问题
 * Created by zhangkang on 2017/6/18.
 */
public class HashMapThread extends Thread {
    private static AtomicInteger ai = new AtomicInteger(0);
    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>(1);

    public void run() {
        while (ai.get() < 100000) {
            map.put(ai.get(), ai.get());
            ai.incrementAndGet();
        }
//        System.out.println(map.size());
    }

    public static void main(String[] args) {
        HashMapThread hmt0 = new HashMapThread();
        HashMapThread hmt1 = new HashMapThread();
        HashMapThread hmt2 = new HashMapThread();
        HashMapThread hmt3 = new HashMapThread();
        HashMapThread hmt4 = new HashMapThread();
        hmt0.start();
        hmt1.start();
        hmt2.start();
        hmt3.start();
        hmt4.start();
    }
}
