package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.intf.EmployeeDao;
import com.autoserve.abc.dao.intf.EmployeeRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author RJQ 2014/11/17 9:54.
 */
public class EmployeeDaoTest extends BaseDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeRoleDao employeeRoleDao;

    @Test
    public void testEmployeeInsert(){
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpName("RJQ");
        employeeDao.insert(employee);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+employee.getEmpId());
    }

    @Test
    public void testEmployeeUpdateSelective(){
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpId(16);
        employee.setEmpState(2);
        employee.setEmpAddress("asdsad");
        int result = employeeDao.updateByPrimaryKeySelective(employee);
        System.out.println("~~!!!!!!!!!!!!!!!!!!!!!!!!"+result);
    }

    @Test
    public void testFindEntityById(){
//        EmployeeDO employeeDO = employeeDao.findEntityById(16);
//        System.out.println("~~~"+employeeDO.getEmpName());
    }

    @Test
    public void testFindEmpByNameAndPass(){
        EmployeeDO employeeDO = employeeDao.findEmpByNameAndPass("RJQ1", "1234");
        System.out.println(employeeDO.getEmpId());
    }

    @Test
    public void testDeleteByEmpId(){
        int returnVal = employeeRoleDao.deleteByEmpId(47);
        System.out.println(returnVal);
    }
}
