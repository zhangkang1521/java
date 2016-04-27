package org.zhangkang.commons;

import org.junit.Test;
import org.zhangkang.commons.utils.DbUtils;

import java.util.Date;

/**
 * Created by Administrator on 2016/4/9.
 */
public class DbUtilsTest {

    @Test
    public void testInsert() throws Exception {
        DbUtils dbUtils = new DbUtils();
        dbUtils.getConnection();
        String sql = "insert into tb_user(id,user_name,age,birthday,create_time) " +
                "values(?,?, ?, ?,?)";
        int affect = dbUtils.executeUpdate(sql, 9, "zk", 21, new Date(),new Date());
        System.out.println(affect);
        dbUtils.close();
    }

    @Test
    public void testDate(){
        Date now = new Date();
        java.sql.Timestamp sqlNow = new java.sql.Timestamp(now.getTime());
        System.out.println(sqlNow);
    }
}
