package com.autoserve.abc.service.biz.impl.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/11/20 14:02.
 */
public class EmployeeServiceTest extends BaseServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testQueryList() {
        PageResult<EmployeeDO> page = employeeService.queryList(new EmployeeDO(), new PageCondition());
        System.out.println("~~~~~~~~~~~~~~~~~~~~" + page.getDataSize());
    }

    //@Test
    public void testFindEntityById() {
        Employee entity = employeeService.findEntityById(16).getData();
        System.out.println("~~~~~~~~~~~~~~" + JSON.toJSON(entity));
        System.out.println("~~~~~~~~~~~~~~" + entity.getEmpName());
    }

    //@Test
    public void testCreateEmployee() {
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpState(1);
        employee.setEmpName("rjq1234");
        BaseResult result = employeeService.createEmployee(employee);
        System.out.println(result.getMessage());
    }

    //@Test
    public void testUpdatePassword() {
        employeeService.updatePassword(25, "123456", "123456789");
    }

    //@Test
    public void testLogin() {
        PlainResult<Employee> result = employeeService.login("rjq123", "e10adc3949ba59abbe56e057f20f883e",
                "192.168.0.0");
        if (!result.isSuccess()) {
            System.out.println(result.getMessage());
        } else {
            System.out.println(result.getData());
        }

    }

    //@Test
    public void testLogout() {
        BaseResult result = employeeService.logout(26, "192.168.0.0");
        System.out.println(result.getMessage());
    }

    //@Test
    public void testQueryEmpWithRolesList() {
        PageResult<Employee> result = employeeService.queryEmpWithRolesList(new EmployeeDO(), new PageCondition());
        System.out.println(result.getDataSize());
    }

    //@Test
    public void test() {
        Employee e = employeeService.findEntityById(27).getData();
        System.out.println(e.toString());
    }

}
