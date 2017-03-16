package org.zk.difficult;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class StrangeMap {
    public static void main(String[] args) {
//        Map<String, Integer> collection = new TreeMap<String, Integer>();
//        collection.put("a", 1);
//        collection.put("b", 3);
//        int result = collection.compute("a", new BiFunction<String, Integer, Integer>() {
//            @Override
//            public Integer apply(String s, Integer o) {
//                System.out.println(s + "," + o);
//                return o + 1;
//            }
//        });
//        System.out.println(result);
//        System.out.println(collection);

        Runnable r = () -> {
            System.out.println("s");
            System.out.println("s2");
        };
        new Thread(r).start();
    }
}
