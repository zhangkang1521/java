package org.zk.other;

import java.sql.*;

/**
 * Created by Administrator on 10/17/2016.
 */
public class JdbcTest {
    public static void main(String[] args) throws Exception{
//        String driver = "oracle.jdbc.driver.OracleDriver";
//        String url = "jdbc:oracle:thin:@localhost:1521:ZK";
//        String username = "scott";
//        String password = "123456";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/zk";
        String username = "root";
        String password = "123456";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement pstmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pstmt.executeQuery("select * from emp");
//        while (rs.next()) {
//            System.out.println(rs.getObject("username"));
//        }
//        rs.previous();
//        rs.previous();
//        rs.updateString("username", "xx2");
//        rs.updateRow();
//        rs.last();
//        System.out.println(rs.getRow());
        rs.next();
        rs.previous();
        rs.close();
        pstmt.close();
        conn.close();
    }
}
