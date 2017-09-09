package org.zk.difficult;

import java.sql.SQLException;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class Mocker<T extends Exception> {

    private void pleaseThrow(final Exception t) throws T {
        //throw new RuntimeException("s");
        throw  (T) t;
    }

    public static void main(String[] args) {
        try {
            new Mocker<RuntimeException>().pleaseThrow(new SQLException());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
