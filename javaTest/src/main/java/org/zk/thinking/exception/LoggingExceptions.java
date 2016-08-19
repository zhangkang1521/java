package org.zk.thinking.exception;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Created by Administrator on 8/13/2016.
 */

class LoggingException extends Exception {


    public LoggingException() {

    }

}

public class LoggingExceptions {

    private static Logger logger = Logger.getLogger("LoggingExceptions");
    private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(LoggingExceptions.class);

    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch (Exception e) {
            // 此处用日志记录异常的完整堆栈轨迹, slf4j有log.error(String, Throwable)方法
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.severe("==================================");
            logger.severe(sw.toString());
            logger.severe("==================================");
        }

    }


}
