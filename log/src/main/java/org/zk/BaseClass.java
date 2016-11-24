package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 11/24/2016.
 */
public class BaseClass {

    // c 输出logger的名称
    // C 输出所在类的名称
    // 子类使用这个logger，名字会变为子类的名称，pattern里用C比较好
    protected Logger logger = LoggerFactory.getLogger(getClass());




    public void a() {
        logger.debug("base.a() line 17");
    }
}
