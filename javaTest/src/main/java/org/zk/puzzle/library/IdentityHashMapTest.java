package org.zk.puzzle.library;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zhangkang on 2016/9/9.
 */
public class IdentityHashMapTest {
    public static void main(String[] args) {
        //判断key是否相等 用 ==
        IdentityHashMap<Key, String> map1 = new IdentityHashMap<Key, String>();
        Key key1 = new Key("zk");
        Key key2 = new Key("zk");
        System.out.println(key1 == key2);
        map1.put(key1, "value1");
        map1.put(key2, "value2");
        System.out.println(map1);

        // 判断key是否相等 hashCode && equals 想等
        HashMap<Key, String> map2 = new HashMap<Key, String>();
        System.out.println(key1.hashCode() == key2.hashCode());
        System.out.println(key1.equals(key2));
        map2.put(key1, "value1");
        map2.put(key2, "value2");
        System.out.println(map2);

        Key key3 = new Key(null);
        Key key4 = new Key(null);
        System.out.println(key3.equals(key4));
    }
}

class Key {

    private String id;

    public Key(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return 37 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) {
            String objId = ((Key) obj).getId();
            return id == null ? objId == null : id.equals(objId);
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
