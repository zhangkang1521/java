package org.zk.other.collection;

import java.util.HashMap;

/**
 * Created by Administrator on 10/19/2016.
 */
public class HashMapTest {
    public static void test() {
//                System.out.println(key1.equals(key1));
//        System.out.println(key1.hashCode() == key2.hashCode());
//        System.out.println(key1.equals(key2));
    }
    public static void main(String[] args) {
        HashMap<Key, String> map = new HashMap<Key, String>(8, 0.75f);
        Key key1 = new Key();
        key1.setId(2);
        Key key2 = new Key();
        key2.setId(10);
        map.put(key1, "aa");
        map.put(key2, "bb");
        System.out.println(map.size());
        System.out.println(map.get(key1));
    }
}
