package org.zk.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zk.BaseTest;
import org.zk.beans.Emp;


/**
 * Created by Administrator on 12/9/2016.
 */
public class EmpDaoTest extends BaseTest{

    @Autowired
    EmpDao dao;

    @Test
    public void testSave(){
        Emp emp = new Emp();
        emp.setEmpNo(1003);
        emp.setEname("zhangyan3");

        Emp emp2 = new Emp();
        emp2.setEmpNo(1004);
        emp2.setEname("zhangyan4");
        dao.save(emp, emp2);
    }

    @Test
    public void testGet(){
        Emp emp = dao.findById(7369);
        System.out.println(emp.getEname());
    }
}