package org.zk.difficult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class Initialize {
    public static void main(String[] args) {
        // http://stackoverflow.com/questions/924285/efficiency-of-java-double-brace-initialization
        // 初始化语法
        final List<String> NAMES = new ArrayList<String>() {{
            add("Jone");
        }};
        System.out.println(NAMES.getClass());
    }
}
