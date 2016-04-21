package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Logger log = LoggerFactory.getLogger(App.class);

   public static void printLog(){
       log.debug("debug test");
   }
}
