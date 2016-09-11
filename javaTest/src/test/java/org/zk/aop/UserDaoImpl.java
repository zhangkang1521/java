package org.zk.aop;

/**
 * Created by root on 16-6-11.
 */
public class UserDaoImpl implements UserDao {
    public int deleteById(int id) {
        System.out.println(" 实现类执行 deleteById");
        return id;
    }
}
