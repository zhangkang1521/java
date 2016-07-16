package org.zk.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.zk.BaseTest;

/**
 * Created by zhangkang on 2016/5/10.
 */
public class UserControllerTest extends BaseTest{


    @Test
    public void test1(){
        String str = "<>'\" aaa bbb";
        System.out.println(StringEscapeUtils.escapeHtml4(str));

    }
}
