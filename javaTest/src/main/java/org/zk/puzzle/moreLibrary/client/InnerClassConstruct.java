package org.zk.puzzle.moreLibrary.client;

import org.zhangkang.commons.utils.ClassUtils;
import org.zk.puzzle.moreLibrary.Outer;

import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 9/21/2016.
 */
public class InnerClassConstruct {
    public static void main(String[] args) throws Exception {
        ClassUtils.showConstructs(Outer.A.class);
        // 成员内部类没有无参的构造器
        //PackageInnerClass.A.class.newInstance();

        Outer p = new Outer();
        Constructor cons = Outer.A.class.getConstructor(Outer.class);
        Outer.A inner = (Outer.A) cons.newInstance(p);

        Outer.A inner2 = p.new A();
    }
}
