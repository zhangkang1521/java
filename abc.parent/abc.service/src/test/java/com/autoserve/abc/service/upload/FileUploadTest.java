package com.autoserve.abc.service.upload;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.citrus.service.requestcontext.session.idgen.uuid.impl.UUIDGenerator;
import com.autoserve.abc.service.biz.impl.BaseServiceTest;
import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
import com.autoserve.abc.service.oss.OssService;

/**
 * @author RJQ 2014/11/21 16:00.
 */
public class FileUploadTest extends BaseServiceTest {
    @Autowired
    private OssService        ossService;

    @Autowired
    private FileUploadService fileUploadService;

    @Test
    public void testOssUploadFile() {
        String filePath = "/Users/sean/Pictures/psb.jpg";
        File file = new File(filePath);
        UUIDGenerator generator = new UUIDGenerator();
        String fileName = generator.generateSessionID().concat(".jpg");
        String bucketName = "autoserve2015";
        String downLink = null;
        try {
            downLink = ossService.uploadFile(file, bucketName, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("~~~~~~~~~~" + downLink);
    }

}
