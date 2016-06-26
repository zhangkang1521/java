package org.zhangkang.commons;

import org.junit.Test;
import org.zhangkang.commons.utils.ClassUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/4.
 */
public class ShowClassTest {

    @Test
    public void test1() {
        ClassUtils.showInherit(C.class);
        ClassUtils.showInherit(ArrayList.class);
    }
}
