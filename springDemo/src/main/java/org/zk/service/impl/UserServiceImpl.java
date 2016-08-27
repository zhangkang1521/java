package org.zk.service.impl;

import org.springframework.stereotype.Service;
import org.zk.beans.User;
import org.zk.service.UserService;

import java.util.Date;

/**
 * Created by zhangkang on 2016/4/27.
 */
@Service
public class UserServiceImpl implements UserService {


    public User findById(Integer id) {
        User user = new User();
        user.setId(id);
        System.out.println("findById"+id);
        System.out.println(this.hashCode());
        return user;
    }

    public void testLock(String id){
//        DistributedLock lock = new DistributedLock();
//        try{
//            lock.lock();
//            System.out.println("lock"+id+":"+new Date());
//            Thread.sleep(2000);
//        }catch(Exception e){
//            e.printStackTrace();
//        } finally {
//
//        }
    }

}
