package com.autoserve.abc.dao;

import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.dao.intf.FileUploadInfoDao;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/25 14:26.
 */
public class FileUploadInfoDaoTest extends BaseDaoTest {
    @Resource
    private FileUploadInfoDao fileUploadInfoDao;

    @Test
    public void testCreate(){
        FileUploadInfoDO uploadInfoDO = new FileUploadInfoDO();
        uploadInfoDO.setFuiState(0);
        uploadInfoDO.setFuiFilePath("asdas");
        uploadInfoDO.setFuiClassType(1);
        int val = fileUploadInfoDao.insert(uploadInfoDO);
        System.out.println(val);
    }
}
