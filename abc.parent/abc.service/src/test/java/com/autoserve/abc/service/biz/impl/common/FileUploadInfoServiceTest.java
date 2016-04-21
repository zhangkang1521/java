package com.autoserve.abc.service.biz.impl.common;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;

/**
 * @author RJQ 2014/12/25 13:40.
 */
public class FileUploadInfoServiceTest extends BaseServiceTest {
    @Resource
    private FileUploadInfoService fileUploadInfoService;

    @Test
    public void testCreateFileUploadInfo() {
        //        FileUploadClassType uploadClassType = FileUploadClassType.EMPLOYEE;
        //        int empId = 24;
        //        String filePath = "test";
        //        BaseResult result = fileUploadInfoService.createFileUploadInfo(uploadClassType, null, empId, null, filePath);
        //        System.out.println(result.getMessage());
    }

    @Test
    public void testQueryList() {
        //        FileUploadInfo fileUploadInfo = new FileUploadInfo();
        //        fileUploadInfo.setFuiClassType(FileUploadClassType.EMPLOYEE);
        //        BaseResult result = fileUploadInfoService.queryList(fileUploadInfo, new PageCondition());
        //        System.out.println(result.getMessage());
    }
}
