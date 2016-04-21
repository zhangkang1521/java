package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.EmployeeRoleDO;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.EmployeeDao;
import com.autoserve.abc.dao.intf.EmployeeRoleDao;
import com.autoserve.abc.dao.intf.MenuBtnDao;
import com.autoserve.abc.dao.intf.MenuDao;
import com.autoserve.abc.dao.intf.RoleDao;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.authority.MenuService;

public class InitSystem extends BaseServiceTest {

    @Resource
    private EmployeeDao     employeeDao;

    @Resource
    private RoleDao         roleDao;

    @Resource
    private EmployeeRoleDao employeeRoleDao;

    @Resource
    private MenuDao         menuDao;

    @Resource
    private MenuBtnDao      menuBtnDao;

    @Resource
    private MenuService     menuService;

    @Test
    public void initSystem() {
        //创建一个员工
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpName("测试员工");
        employeeDao.insert(employee);
        int empIndex = employee.getEmpId();
        System.err.println(empIndex);
        //创建一个角色
        RoleDO role = new RoleDO();
        role.setRoleName("测试角色");
        role.setRoleSort(0);
        roleDao.insert(role);
        int roleIndex = role.getRoleId();
        System.err.println(roleIndex);
        EmployeeRoleDO erdo = new EmployeeRoleDO();
        erdo.setEmpId(empIndex);
        erdo.setRoleId(roleIndex);
        employeeRoleDao.insert(erdo);

        MenuDO menu1 = new MenuDO();
        menu1.setMenuName("系统管理");
        menu1.setMenuUrl("#");
        menu1.setMenuParent(0);
        menuDao.insert(menu1);
        int m1Index = menu1.getMenuId();
        MenuDO menu2 = new MenuDO();
        menu2.setMenuName("菜单管理");
        menu2.setMenuUrl("/manager/menu");
        menu2.setMenuParent(m1Index);
        menuDao.insert(menu2);
        int m2I = menu2.getMenuId();
        MenuDO menu3 = new MenuDO();
        menu3.setMenuName("角色管理");
        menu3.setMenuUrl("/manager/role");
        menu3.setMenuParent(m1Index);
        menuDao.insert(menu3);
        int m3I = menu3.getMenuId();
        List<Integer> btns = new ArrayList<Integer>();
        btns.add(138);
        btns.add(139);
        menuService.allocBtn(m1Index, btns);
        menuService.allocBtn(m2I, btns);
        menuService.allocBtn(m3I, btns);

    }
}
