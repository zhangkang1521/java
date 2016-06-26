package org.zhangkang.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 数据库实用工具类
 */
public class DbUtils {

    private static Logger LOG = LoggerFactory.getLogger(DbUtils.class);

    private Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rs;

    private static String driverName;
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            // 读取配置文件
            ResourceBundle rb = ResourceBundle.getBundle("jdbc");
            driverName = rb.getString("jdbc.driverClassName");
            url = rb.getString("jdbc.url");
            username = rb.getString("jdbc.username");
            password = rb.getString("jdbc.password");

            //Class.forName(driverName);
        } catch (Exception e) {
            LOG.debug("加载数据库驱动失败", e);
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
        LOG.debug("sql:{}", sql);
        for (int i = 0; i < args.length; i++) {
            pStmt.setObject(i + 1, args[i]);
            LOG.debug((i + 1) + ":"
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
        LOG.debug("sql:{}", sql);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Date) {
                pStmt.setTimestamp(i + 1, new java.sql.Timestamp(((Date) args[i]).getTime()));
            } else {
                pStmt.setObject(i + 1, args[i]);
            }
            LOG.debug((i + 1) + ":"
                    + args[i].getClass().getSimpleName() + "(" + args[i] + ")");
        }
        return pStmt.executeUpdate();
    }

    /**
     * 关闭资源
     *
     * @throws SQLException
     */
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pStmt != null) {
                pStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            LOG.error("关闭数据库异常", e);
        }
    }
}
