package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.GovernmentVOConverter;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.government.GovernmentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/10 14:11.
 */
public class EditGov {
    @Resource
    private GovernmentService governmentService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(EditGov.class);

    public JsonBaseVO execute(@Param("org") String org, @Param("govId") Integer govId) {
        JsonBaseVO vo = new JsonBaseVO();
        logger.info("机构修改参数 org={}, govId={}, empId={}", org, govId, LoginUserUtil.getEmpId());
        if (org == null) {
            vo.setSuccess(false);
            vo.setMessage("参数不正确");
            logger.warn("机构修改参数不正确 org={}, govId={}, empId={}", org, govId, LoginUserUtil.getEmpId());
            return vo;
        }

        GovernmentVO gov = JSONObject.parseObject(org, GovernmentVO.class);
        Integer govUpdateEmpId = LoginUserInfoHelper.getLoginUserInfo().getEmpId();
        GovPlainJDO govPlainJDO = GovernmentVOConverter.convertToGovPlainDO(gov);
        govPlainJDO.setGovId(govId);
        BaseResult result = governmentService.modifyGovernment(govPlainJDO, govUpdateEmpId);
        vo = ResultMapper.toBaseVO(result);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改了ID为"+ govId +"的机构");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
