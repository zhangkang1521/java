package org.zk.aop.cglib;

import net.sf.cglib.beans.BeanGenerator;
import org.zhangkang.commons.utils.ClassUtils;

/**
 * Created by zhangkang on 2016/8/29.
 */
public class CglibTest {
    public static void main(String[] args) throws Exception {
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("id", Integer.class);
        generator.addProperty("username", String.class);
        Object obj = generator.create();
        ClassUtils.showMethods(obj.getClass());

    }
}
