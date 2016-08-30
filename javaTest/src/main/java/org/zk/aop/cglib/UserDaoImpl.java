package org.zk.aop.cglib;

/**
 * Created by root on 16-6-11.
 */
public class UserDaoImpl {
    public int deleteById(int id) {
        System.out.println("deleteById");
        return id;
    }
}
