package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.GovGuarWithNameDO;
import com.autoserve.abc.dao.intf.GovGuarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/9 15:52.
 */
public class GovGuarDaoTest extends BaseDaoTest{
    @Autowired
    private GovGuarDao govGuarDao;

    @Test
    public void testFindByList(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(5);
        list.add(6);
        List<GovGuarWithNameDO> resut = govGuarDao.findByList(list);
        System.out.println(resut.toString());
    }
}
