package org.zhangkang.commons.utils;

import org.junit.Test;

import java.sql.ResultSet;
import java.util.Enumeration;
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

    @Test
    public void testUpdate() throws Exception{
        DbUtils db = new DbUtils();
        db.executeUpdate("update tb_user set age=? where id=?;drop table tb_user;", 40, 3);
        db.close();
    }

    @Test
    public void testSql(){
        DbUtils db = new DbUtils();
        String sql = "update tb_user set age=22 where id=";
        sql += "1 and database()='zk'";
        //int row = db.executeSql(sql);
        db.close();
    }

    @Test
    public void testResource() throws Exception{
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        //
        Enumeration em = systemClassLoader.getResources("");
        while (em.hasMoreElements()){
            System.out.println(em.nextElement());
        }
    }
}
