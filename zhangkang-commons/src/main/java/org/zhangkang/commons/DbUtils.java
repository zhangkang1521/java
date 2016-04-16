package org.zhangkang.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 数据库实用工具类
 */
public class DbUtils {

    private Connection conn;

    private PreparedStatement pStmt;

    private ResultSet rs;

    private static String driverName = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:globaldb";
    private static String username = "scott";
    private static String password = "123456";

    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(
                url, username, password);
        return conn;
    }

    /**
     * 执行查询
     *
     * @param sql  预编译的sql
     * @param args 参数
     * @return 结果集
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object... args)
            throws SQLException {
        if (conn == null) {
            conn = this.getConnection();
        }
        pStmt = conn.prepareStatement(sql);
        System.out.println(sql);
        for (int i = 0; i < args.length; i++) {
            pStmt.setObject(i + 1, args[i]);
            System.out.println((i + 1) + ":"
                    + args[i].getClass().getSimpleName() + "(" + args[i] + ")");
        }
        rs = pStmt.executeQuery();
        return rs;
    }

    /**
     * 执行更新
     *
     * @param sql  预编译的sql
     * @param args 参数
     * @return 影响行数
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object... args) throws SQLException {
        if (conn == null) {
            conn = this.getConnection();
        }
        pStmt = conn.prepareStatement(sql);
        System.out.println(sql);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Date) {
                pStmt.setTimestamp(i + 1, new java.sql.Timestamp(((Date) args[i]).getTime()));
            } else {
                pStmt.setObject(i + 1, args[i]);
            }
            System.out.println((i + 1) + ":"
                    + args[i].getClass().getSimpleName() + "(" + args[i] + ")");
        }
        return pStmt.executeUpdate();
    }

    /**
     * 关闭资源
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pStmt != null) {
            pStmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
