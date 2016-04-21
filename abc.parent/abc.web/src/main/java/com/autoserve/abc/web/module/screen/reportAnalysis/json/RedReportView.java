/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;


public class RedReportView {
    private static final Logger      logger = LoggerFactory.getLogger(RedReportView.class);
    @Resource
    private RedService redService;

    public JsonPageVO<RedReportDO> execute(ParameterParser params) {
        String model = params.getString("model");
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
        FullTransferFundsSearchDO search = null;
        try {
            if (model != null) {
            	
            }
        } catch (Exception e) {
            logger.warn("参数解析失败");
        }
        JsonPageVO<RedReportDO> resultVO = new JsonPageVO<RedReportDO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<RedReportDO> result = redService.redReport( pageCondition);
        resultVO.setTotal(result.getTotalCount());
        resultVO.setRows(result.getData());
        return resultVO;
    }
}
