package impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Created by Administrator on 2016/4/23.
 */
public class StaticLoggerBinder  implements LoggerFactoryBinder {

    private static final String loggerFactoryClassStr = MyLoggerFactory.class.getName();

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public ILoggerFactory getLoggerFactory() {
        System.out.println("invoke getLoggerFacoty()");
        return new MyLoggerFactory();
    }

    public String getLoggerFactoryClassStr() {
        System.out.println("invoke getLoggerFactoryClassStr()"+loggerFactoryClassStr);
        return loggerFactoryClassStr;
    }

    public static final StaticLoggerBinder getSingleton() {
        System.out.println("invoke singleton()");
        return SINGLETON;
    }
}
