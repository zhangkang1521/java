package org.zhangkang.commons;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/4.
 */
public class ShowClassTest {

   // @Test
    public void test1() {
        ShowClass.showInherit(C.class);
        ShowClass.showInherit(ArrayList.class);
    }
}
