package impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Created by Administrator on 2016/4/23.
 */
public class MyLoggerFactory implements ILoggerFactory {
    public Logger getLogger(String s) {
        return new MyLogger();
    }
}
