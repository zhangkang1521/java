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

   public static void printLogAA(){
      try{
          printLogA();
//          int a = 0;
//            int c =  5/a;
      } catch (Exception e){
          log.error("{}{}",1,2);
          log.error("调用logA失败！", e);
      }
      log.debug("printLogAA()");
   }

    public static void printLogA(){
        log.debug("invoke printLogA()");
       throw new RuntimeException("xxx");
    }
}
