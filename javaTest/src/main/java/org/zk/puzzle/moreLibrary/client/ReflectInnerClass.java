package org.zk.puzzle.moreLibrary.client;

import org.zk.puzzle.moreLibrary.Outer;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Administrator on 9/19/2016.
 */
public class ReflectInnerClass {
    public static void main(String[] args) throws Exception {
        Outer outer = new Outer();
        // B 是包访问类型的，即使hashCode是public方法，也不能访问
        //outer.getB().hashCode();
        ((Object) outer.getB()).hashCode();

        HashSet<String> set = new HashSet<String>();
        set.add("111");
        Iterator it = set.iterator();
        System.out.println(it.hasNext());
        System.out.println(it.getClass());
        // 用这种方式取反射方法有潜在的危险 ，内部类包访问权限，就有问题了
        //Method m = it.getClass().getMethod("hasNext");
        Method m = Iterator.class.getMethod("hasNext");
        System.out.println(m.invoke(it));

    }
}
