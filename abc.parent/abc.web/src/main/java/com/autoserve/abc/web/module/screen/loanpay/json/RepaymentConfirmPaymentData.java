package com.autoserve.abc.web.module.screen.loanpay.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 类RepaymentConfirmPaymentData.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2015年1月10日 下午11:43:32
 */
public class RepaymentConfirmPaymentData {
    @Resource
    private RepayService        repayService;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private ImageCaptchaService imageCaptchaService;
    @Resource
    private SysConfigService    sysConfigService;
    @Resource
    private PaymentPlanService  paymentPlanService;

    public JsonBaseVO execute(Context context, ParameterParser params, @Param("type") String type) {

        String sessionId = request.getSession().getId();

        Boolean isResponseCorrect = false;

        BaseResult result = new BaseResult();

        int loanId = params.getInt("loanId");

        int planId = params.getInt("planId");

        PayType payType = null;
        if (type.equals("Normal")) {
            payType = PayType.COMMON_CLEAR;
        }
        if (type.equals("Replace")) {
            payType = PayType.PLA_CLEAR;
        }
        if (type.equals("Compel")) {
            payType = PayType.FORCE_CLEAR;
        }
        if (payType == null) {
            result.setMessage("还款类型异常！");
            result.setSuccess(false);
        }
        if (payType == PayType.PLA_CLEAR) {

            PlainResult<SysConfig> sysResult = this.sysConfigService
                    .querySysConfig(SysConfigEntry.CONTINUE_DESIGNATED_PAY);

            if (sysResult.getData() != null && sysResult.getData().getConfValue() != "0") {
                paymentPlanService.queryNextPaymentPlan(loanId);

            }

        }

        String securityCode = params.getString("securityCode");

        isResponseCorrect = imageCaptchaService.validateResponseForID(sessionId, securityCode);

        if (isResponseCorrect) {
            result = this.repayService.repay(loanId, planId, payType, LoginUserUtil.getEmpId());
        } else {
            result.setMessage("验证码错误");
            result.setSuccess(false);
        }

        return ResultMapper.toBaseVO(result);

    }
}
