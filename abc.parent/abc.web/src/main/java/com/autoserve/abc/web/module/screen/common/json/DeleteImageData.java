package com.autoserve.abc.web.module.screen.common.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除影像资料
 *
 * @author RJQ 2014/12/28 21:28.
 */
public class DeleteImageData {

    @Autowired
    private FileUploadInfoService infoService;

    public JsonBaseVO execute(@Param("fileId") Integer fileId) {
        JsonBaseVO vo = new JsonBaseVO();
        if(fileId >= 0){
            BaseResult result = infoService.removeFileUploadInfoById(fileId);
            return ResultMapper.toBaseVO(result);
        }
        vo.setSuccess(false);
        vo.setMessage("删除失败！");
        return vo;
    }
}
