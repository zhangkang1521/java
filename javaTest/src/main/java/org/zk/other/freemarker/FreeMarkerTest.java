package org.zk.other.freemarker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 1/7/2017.
 */
public class FreeMarkerTest {
    static FreemarkerUtil fu = new FreemarkerUtil();
    static Map<String,Object> root = null;

    public static void main(String[] args) {
        //1、创建数据模型
        Map<String,Object> root = new HashMap<String,Object>();
        //2、为数据模型添加值
        root.put("username", "张三");
        //3、将数据模型和模板组合的数据输出到控制台
        fu.print("01.tpl", root);
        //fu.fprint("02.ftl", root, "01.html");
    }
}
