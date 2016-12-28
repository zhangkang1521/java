package org.zk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zk.beans.Emp;

/**
 * Created by Administrator on 12/9/2016.
 */
//@Repository
public class EmpDao {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Transactional
    public void save(Emp emp, Emp emp2){
        hibernateTemplate.save(emp);
        int i = 2;
        if(i == 1)
            throw new RuntimeException("ee");
        hibernateTemplate.save(emp2);
    }

    public Emp findById(Integer empno) {
        return hibernateTemplate.get(Emp.class, empno);
    }


}
