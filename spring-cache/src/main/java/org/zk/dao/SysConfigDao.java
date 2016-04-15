package org.zk.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by zhangkang on 2016/4/15.
 */
@Repository
public class SysConfigDao {

    @Cacheable(value = "A", key = "'sys'+#code")
    public String findByCode(String code) {
        System.out.println("findByCode:" + code);
        if (code != null) {
            return code.toUpperCase();
        } else {
            return code;
        }
    }

    @Cacheable(value = "B", key = "'sys'+#code")
    public String findByCode2(String code) {
        System.out.println("findByCode:" + code);
        if (code != null) {
            return code.toUpperCase();
        } else {
            return "";
        }
    }
}
