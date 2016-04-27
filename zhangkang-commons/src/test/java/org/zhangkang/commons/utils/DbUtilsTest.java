package org.zhangkang.commons.utils;

import org.junit.Test;

import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by zhangkang on 2016/4/27.
 */
public class DbUtilsTest {

    @Test
    public void test1(){
      ResourceBundle  rb = ResourceBundle.getBundle("jdbc");
        String cls = rb.getString("jdbc.driverClassName");
        System.out.println(cls);
    }

    @Test
    public void test2() throws Exception{
        DbUtils db = new DbUtils();
        ResultSet rs = db.executeQuery("select 1 from dual");
        if(rs.next()){
            Object obj = rs.getObject(1);
            System.out.println(obj);
        }
    }
}
