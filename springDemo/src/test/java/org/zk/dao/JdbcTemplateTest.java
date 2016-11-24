package org.zk.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlUpdate;
import org.zk.beans.Emp;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Administrator on 11/19/2016.
 */
public class JdbcTemplateTest {

    JdbcTemplate jdbcTemplate;
    DriverManagerDataSource dataSource;

    @Before
    public void setJdbcTemplate() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:ZK");
        dataSource.setUsername("scott");
        dataSource.setPassword("123456");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testExecute() {
        jdbcTemplate.execute("update emp set sal=802 where empno=7369");
        //jdbcTemplate.execute("update emp set sal=800 where empno=7369");
    }

    @Test
    public void testQuery() {
        MappingSqlQuery query = new MappingSqlQuery() {
            @Override
            protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Emp emp = new Emp();
                emp.setEmpNo(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                return emp;
            }
        };
        query.setDataSource(dataSource);
        query.setSql("select * from emp where empno=?");
        query.declareParameter(new SqlParameter(Types.INTEGER));
        //query.compile();
        List list = query.execute(new Object[]{7369});
        System.out.println(((Emp)list.get(0)).getEname());
    }


    @Test
    public void testUpdate() {
        SqlUpdate sqlUpdate = new SqlUpdate();
        sqlUpdate.setDataSource(dataSource);
        sqlUpdate.setSql("update emp set sal=? where empno=?");
        sqlUpdate.declareParameter(new SqlParameter(Types.NUMERIC));
        sqlUpdate.declareParameter(new SqlParameter(Types.NUMERIC));
        //sqlUpdate.compile();
        int affectRow = sqlUpdate.update(new Object[]{801, 7369});
        System.out.println(affectRow);

    }

    @Test
    public void testFunction() {
        SqlFunction fun = new SqlFunction();
        fun.setDataSource(dataSource);
        fun.setSql("select count(*) from emp");
        fun.compile();
        int count = fun.run();
        System.out.println(count);
    }
}
