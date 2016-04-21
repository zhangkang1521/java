package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 类getMoreInvestInfoJSON.java的实现描述：Ajax 翻译查询我的投资信息
 * 
 * @author WangMing 2015年5月18日 上午10:31:03
 */
public class getMoreInvestInfoJSON {

    @Autowired
    private InvestQueryService investQueryService;

    @Resource
    private HttpSession        session;

    //日期格式化
    private SimpleDateFormat   dateFormat       = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat   simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //数字格式化
    private String             format           = "###,###,###";
    private NumberTool         nt               = new NumberTool();

    public String execute(Context context, ParameterParser params) {

        User user = (User) session.getAttribute("user");

        StringBuffer html = new StringBuffer();
        String investType = params.getString("investType");

        Integer pageSize = 5;
        Integer currentPage = params.getInt("currentPage") + 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        InvestSearchJDO investSearchJDO = new InvestSearchJDO();
        investSearchJDO.setUserId(user.getUserId());
        //回款中
        if (investType != null && "HKZ".equals(investType)) {
            //未支付
            PageResult<MyInvestReceived> pageResult = investQueryService.queryMyInvestReceived(investSearchJDO,
                    pageCondition);
            //分页处理
            Pagebean<MyInvestReceived> pagebean = new Pagebean<MyInvestReceived>(currentPage, pageSize,
                    pageResult.getData(), pageResult.getTotalCount());
            html = addHKZHtml(pagebean.getRecordList());
        }
        //投标中
        else if (investType != null && "TBZ".equals(investType)) {
            //待收益
            PageResult<MyInvestTender> pageResult = investQueryService.queryMyInvestTender(investSearchJDO,
                    pageCondition);
            //分页处理
            Pagebean<MyInvestTender> pagebean = new Pagebean<MyInvestTender>(currentPage, pageSize,
                    pageResult.getData(), pageResult.getTotalCount());
            context.put("pagebean", pagebean);
            Map<String, BigDecimal> resultLoanListMap = new HashMap<String, BigDecimal>();
            for (MyInvestTender temp : pagebean.getRecordList()) {
                BigDecimal percent = temp.getValidInvest().divide(temp.getLoanMoney(), 50, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100));
                resultLoanListMap.put(temp.getLoanId(), percent);
            }
            html = addTBZHtml(pagebean.getRecordList(), resultLoanListMap);
        }
        //已回款
        else if (investType != null && "YHK".equals(investType)) {
            PageResult<MyInvestClean> pageResult = investQueryService
                    .queryMyInvestClean(investSearchJDO, pageCondition);
            //分页处理
            Pagebean<MyInvestClean> pagebean = new Pagebean<MyInvestClean>(currentPage, pageSize, pageResult.getData(),
                    pageResult.getTotalCount());
            html = addYHKHtml(pagebean.getRecordList());
        }
        return html.toString();
    }

    /**
     * 拼接回款中的投资信息
     * 
     * @param myInvestReceiveds
     * @return
     */
    private StringBuffer addHKZHtml(List<MyInvestReceived> myInvestReceiveds) {
        StringBuffer html = new StringBuffer();
        for (MyInvestReceived mir : myInvestReceiveds) {
            html.append("<div class='container clearfix c-bg-white mt10'>");
            html.append("<a href='/invest/investDetail?loanId = " + mir.getLoanId() + " & flagLoan=1'>");
            html.append("<div class='clearfix con_top'>");
            html.append("<span>");
//            if (mir.getLoanNo().length() > 25) {
//                html.append(mir.getLoanNo().substring(0, 25) + "...");
//            } else {
                html.append(mir.getLoanNo());
//            }
            html.append("</span>");
            html.append("</div>");
            html.append("</a>");
           
            html.append("<ul class='clearfix wdtz_list list-unstyled mt10'>");
            html.append("<li class='pull-left'><span>投资金额</span><span class='fc_black'><em>￥"
                    + nt.format(format, mir.getInvestMoney()) + "</em></span></li>");
            html.append("<li class='pull-left' style='padding-left:10px'><span>待收本金</span><span class='fc_black'><em>￥"
                    + nt.format(format, mir.getDsMoney()) + "</em></span></li>");
            html.append("<li class='pull-left'><span>下期回款日</span><span class='fc_black'><em>"
                    + simpleDateFormat.format(mir.getReceivedDate()) + "</em></span></li>");
            html.append("<li class='pull-left' style='padding-left:10px'><span>下期回款金额</span><span class='fc_black'><em>￥"
                    + nt.format(format, mir.getReceivedMoney()) + "</em></span></li>");
            html.append("<li class='pull-left'><span>投资时间</span><span class='fc_black'><em>"
                    + simpleDateFormat.format(mir.getInvestDate()) + "</em></span></li>");
            html.append("</ul>");
            html.append("<p class='tzqx'><span class='ml10'>到期日：" + simpleDateFormat.format(mir.getEndDate())
                    + "</span></p>");
            html.append("</div>");
        }
        return html;
    }

    /**
     * 拼接投标中的投资信息
     * 
     * @param myInvestTenders
     * @return
     */
    private StringBuffer addTBZHtml(List<MyInvestTender> myInvestTenders, Map<String, BigDecimal> resultLoanListMap) {
        StringBuffer html = new StringBuffer();
        for (MyInvestTender mit : myInvestTenders) {
        	BigDecimal percent = Arith.calcPercent(mit.getValidInvest(), mit.getLoanMoney(), LoanState.valueOf(mit.getLoanState()));
            html.append("<div class='container clearfix c-bg-white mt10'>");
//            html.append("<div class='container clearfix'>");
//            html.append("<div class='index_list-l'>");
            
            //项目名称
            html.append("<a href='/invest/investDetail?loanId = " + mit.getLoanId() + " & flagLoan=1''>");
            html.append("<div class='clearfix con_top'>");
            html.append("<span>");
//            if (mit.getLoanNo().length() > 6) {
//                html.append(mit.getLoanNo().subSequence(0, 6) + "...");
//            } else {
                html.append(mit.getLoanNo());
//            }
            html.append("</span>");
            html.append("</div>");
            html.append("</a>");
            
            html.append("<ul class='clearfix wdtz_list list-unstyled mt10'>");
            html.append("<li class='pull-left'><span>项目金额</span><span class='fc_black'><em>￥"
                    + nt.format(format, mit.getLoanMoney()) + "</em></span></li>");
            html.append("<li class='pull-left' style='margin-left:10px'><span>投资金额</span><span class='fc_black'><em>￥"
                    + nt.format(format, mit.getInvestMoney()) + "</em></span></li><br>");
			html.append("<li class='pull-left'><span>投标进度</span><span class='fc_black'><em>"
					+ percent + "%</em></span></li><br>");
            html.append("<li class='pull-left'><span>投标时间</span><span class='fc_black'><em>"
                    + DateUtil.formatDate(mit.getTenderDate()) + "</em></span></li>");
//            html.append("<li class='pull-left'><canvas id='circle-progress-" + mit.getInvestId()
//                    + "' width='50' height='50'></canvas></li>");
            html.append("<input type='hidden' id='loanCurrentValidInvest_" + mit.getInvestId() + "' value='"
                    + resultLoanListMap.get("" + mit.getLoanId() + "").intValue() + "'>");
            html.append("<input type='hidden' id='loanid_" + mit.getInvestId() + "' value='" + mit.getInvestId() + "'>");
            html.append("</ul>");
            html.append("<p class='tzqx'>");
            html.append("<span>期限：");
            html.append(mit.getLoanPeriod());
            if (mit.getLoanPeriodType() == 1) {
                html.append("年");
            } else if (mit.getLoanPeriodType() == 2) {
                html.append("个月");
            } else if (mit.getLoanPeriodType() == 3) {
                html.append("日");
            }
            html.append("</span>");
            html.append("<span class='ml10'>还款方式：");
            if (mit.getLoanPayType() == 1) {
                html.append("等额本息");
            } else if (mit.getLoanPayType() == 2) {
                html.append("按月付息到期还本");
            } else if (mit.getLoanPayType() == 3) {
                html.append("等额本金");
            } else if (mit.getLoanPayType() == 4) {
                html.append("利随本清");
            }
            html.append("</span>");
            html.append("</p>");
           
//            html.append("</div>");
//            html.append("</div>");
            html.append("</div>");
        }
        return html;
    }

    /**
     * 拼接已回款的投资信息
     * 
     * @param myInvestCleanS
     * @return
     */
    private StringBuffer addYHKHtml(List<MyInvestClean> myInvestCleanS) {
        StringBuffer html = new StringBuffer();
       
        for (MyInvestClean mic : myInvestCleanS) {
            html.append("<div class='container clearfix c-bg-white mt10'>");
//            html.append("<div class='container clearfix'>");
//            html.append("<div class='index_list-l'>");
            html.append("<a href='/invest/investDetail?loanId = " + mic.getLoanId() + " & flagLoan=1''>");
            html.append("<div class='clearfix con_top'>");
            html.append("<span>");
//            if (mic.getLoanNo().length() > 25) {
//                html.append(mic.getLoanNo().substring(0, 25) + "...");
//            } else {
                html.append(mic.getLoanNo());
//            }
            html.append("</span>");
            html.append("</div>");
            html.append("</a>");
          
            html.append("<ul class='clearfix wdtz_list list-unstyled mt10'>");
            html.append("<li class='pull-left'><span>项目金额</span><span class='fc_black'><em>"
                    + nt.currency(mic.getLoanMoney()) + "</em></span></li>");
            html.append("<li class='pull-left' style='margin-left:10px'><span>投资金额</span><span class='fc_black'><em>"
                    + nt.currency(mic.getInvestMoney()) + "</em></span></li>");
            html.append("<li class='pull-left'><span>回款金额</span><span class='fc_black'><em>"
                    + nt.currency(mic.getReceivedMoney()) + "</em></span></li>");
            
            html.append("<li class='pull-left' style='margin-left:10px'><span>状态</span><span class='fc_black'><em>");
            if (mic.getState() == 5) {
                html.append("被转让");
            } else if (mic.getState() == 7) {
                html.append("收益完成");
            }
            html.append("</em></span></li>");
            
            html.append("</ul>");
            html.append("<p class='tzqx'>");
            html.append("<span>投资时间：");
            html.append(DateUtil.formatDate(mic.getInvestDate()));
            html.append("</span>");
//            html.append("<span class='ml10'>状态：");
//            if (mic.getState() == 5) {
//                html.append("被转让");
//            } else if (mic.getState() == 7) {
//                html.append("收益完成");
//            }
//            html.append("</span>");
            html.append("</p>");
            html.append("</a>");
//            html.append("</div>");
//            html.append("</div>");
            html.append("</div>");
        }
        return html;
    }
}
