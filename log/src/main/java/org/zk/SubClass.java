package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 11/24/2016.
 */
public class SubClass extends BaseClass {

   // Logger logger = LoggerFactory.getLogger("sub");

    public void b() {
        a();
        logger.debug("subClass.b() line 15");
    }
}
