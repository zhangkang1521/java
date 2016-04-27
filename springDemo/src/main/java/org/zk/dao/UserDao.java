package org.zk.dao;

import org.springframework.stereotype.Component;
import org.zk.aop.Sql;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Component
public class UserDao {


    @Sql(type= Sql.SqlType.UPDATE, value="delete from tb_user where id=?")
    public int deleteById(Integer id){
        return 1;
    }
}
