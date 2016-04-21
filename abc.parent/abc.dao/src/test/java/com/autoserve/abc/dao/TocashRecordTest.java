package com.autoserve.abc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.intf.TocashRecordDao;

/**
 * 类TocashRecordTest.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月23日 下午4:42:17
 */
public class TocashRecordTest extends BaseDaoTest {

    @Resource
    private TocashRecordDao TocashRecordDao;

    //@Test
    public void selectInvestorExtrTest() {
        TocashRecordJDO tocashRecordJDO = new TocashRecordJDO();
        PageCondition pageCondition = new PageCondition();
        List<TocashRecordJDO> list = this.TocashRecordDao.userInvestorExtr(tocashRecordJDO, pageCondition, null, null);
        System.out.println(list.size());
    }

    @Test
    public void insertTest() {
        TocashRecordDO tocashRecordDO = new TocashRecordDO();
        tocashRecordDO.setTocashSeqNo("11111");
        tocashRecordDO.setTocashState(2);
        tocashRecordDO.setTocashSeqNo("11111");
        this.TocashRecordDao.insert(tocashRecordDO);

    }

}
