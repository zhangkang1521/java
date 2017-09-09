package org.zk.other.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 内部维护了一个双向循环链表，accessOrder用于实现LRU算法
 * https://mp.weixin.qq.com/s/m2XfI2A2jJqFLAI_iNZI-g
 * Created by zhangkang on 2017/6/19.
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(16, 0.75f, true);
        map.put("1", "111");
        map.put("2", "222");
        map.put("3", "222");
        map.get("2");
        for(Map.Entry entry: map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
