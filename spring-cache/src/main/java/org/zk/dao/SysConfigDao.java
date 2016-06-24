package org.zk.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.zk.entity.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by zhangkang on 2016/4/15.
 */
@Service
public class SysConfigDao {

    @Cacheable(value = "user", key = "'myUser'")
    public User findUser(){
        System.out.println("findUser()");
        User user = new User();
        user.setAge(23);
        user.setUserName("zk");
        return user;
    }

    @Cacheable(value = "findParam", key = "'findByParam'+#page+'-'+#pageSize+'-'+#param", condition = "#page==1 && #pageSize==10 && 400==#param.get('userId') && 'active'.equals(#param.get('status'))")
    public String findByParam(int page,int pageSize, Map<String,Object> param){
        System.out.println("invoke findByParam");
        return "xxx";
    }

    @Cacheable(value = "A", key = "'sys_'+#code")
    public String findByCodeA(String code) {
        System.out.println("findByCodeA:" + code);
        if (code != null) {
            return code.toUpperCase();
        } else {
            return code;
        }
    }

    @Cacheable(value="testExpire", key = "1")
    public String testExpire(){
        return "xxx";
    }


    @Cacheable(value = "B", key = "'sys_'+#code")
    public String findByCodeB(String code) {
        System.out.println("findByCodeB:" + code);
        if (code != null) {
            return code.toUpperCase();
        } else {
            return "";
        }
    }

    @CacheEvict(value = "findParam",allEntries = true)
    public void addA(String code){
        System.out.println("evict A "+code);
    }


    @Cacheable(value = "find")
    public String find(){
        System.out.println("invoke find()");
        return "hello";
    }

    @Cacheable(value = "find2")
    public String find2(){
        System.out.println("invoke find2()");
        return "hello2";
    }
}
