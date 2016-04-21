package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.dao.dataobject.stage.statistics.StatisticsPaymentPlan;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.SafeUtil;

public class AccountOverview {
    private final static Logger   logger = LoggerFactory.getLogger(AccountOverview.class); //加入日志

    @Autowired
    private HttpSession           session;
    @Resource
    private UserService           userService;
    @Resource
    private LoanQueryService      loanService;
    @Autowired
    private AccountInfoService    accountInfoService;
    @Resource
    private DoubleDryService      doubleDryService;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private HttpServletRequest    request;
    @Autowired
    private DeployConfigService   deployConfigService;
    @Resource
    private DealRecordService     dealrecordservice;
    @Resource
    private InvestService         investservice;
    @Resource
    private IncomePlanService     incomeplanservice;
    @Resource
    LoanQueryService              loanQueryService;
    @Resource
    private BankInfoService       bankInfoService;
    @Autowired
    private CashInvesterService   cashInvesterService;
    @Resource
    private CashBorrowerService   cashBorrowerService;
    @Resource
    private LevelService          levelService;

    public void execute(Context context, ParameterParser params, Navigator nav) {
        logger.info("into AccountOverview excute");

        User user = (User) session.getAttribute("user");

        if (user == null) {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        }
        Integer completed = 0;
        if (user != null) {
            PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
            ListResult<BankInfoDO> banks = bankInfoService.queryListBankInfo(user.getUserId());
            userDO.getData().setUserBankcardIsbinded(banks.getData().size() > 0 ? 1 : 0);
            context.put("user", userDO.getData());
            if (userDO.getData().getUserPhone() != null && "" != userDO.getData().getUserPhone()) {
                String userPhone = SafeUtil.hideMobile(userDO.getData().getUserPhone());
                context.put("userPhone", userPhone);
                completed += 15;
            }
            if (userDO.getData().getUserDocNo() != null && "" != userDO.getData().getUserDocNo()) {
                String userDocNo = SafeUtil.hideIDNumber(userDO.getData().getUserDocNo());
                context.put("userDocNo", userDocNo);
                completed += 15;
            }
            if (userDO.getData().getUserRealName() != null && "" != userDO.getData().getUserRealName()) {
                String userRealName = SafeUtil.hideName(userDO.getData().getUserRealName());
                context.put("userRealName", userRealName);
                completed += 10;
            }

            if (userDO.getData().getUserName() != null && "" != userDO.getData().getUserName()) {
                completed += 10;
            }
            if (userDO.getData().getUserEmail() != null && "" != userDO.getData().getUserEmail()) {
                completed += 15;
            }
            if (userDO.getData().getUserPwd() != null && "" != userDO.getData().getUserPwd()) {
                completed += 15;
            }
            if (userDO.getData().getUserDocType() != null && "" != userDO.getData().getUserDocType()) {
                completed += 10;
            }
            if (userDO.getData().getUserHeadImg() != null && "" != userDO.getData().getUserHeadImg()) {
                completed += 10;
            }
            context.put("completed", completed);

            //add by wangning 2015-07-06
//            if (userDO.getData().getUserScore() != null && userDO.getData().getUserScore() >= 0) {
//                BigDecimal dcmail_score = new BigDecimal(userDO.getData().getUserScore());
//                PlainResult<LevelDO> levelDo = levelService.findLevelByScore(dcmail_score);
//                if (null != levelDo.getData()) {
//                    context.put("levelIcon", levelDo.getData().getLevIcon());
//                }
//            }
        }

        //待收金额、待还金额
        /*
         * PlainResult<Map<String, BigDecimal>> planinResult =
         * investservice.findTotalIncomeAndPayMoneyByUserId(user .getUserId());
         * context.put("incomeAndPayMap", planinResult.getData());
         */

        //优选推荐
        ListResult<Loan> loanList = this.loanQueryService.queryOptimization(5);
        context.put("loanList", loanList.getData());
        Map<Integer, BigDecimal> loanListMap = new HashMap<Integer, BigDecimal>();
        for (Loan temp : loanList.getData()) {
            BigDecimal percent = temp.getLoanCurrentValidInvest()
                    .divide(temp.getLoanMoney(), 50, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            loanListMap.put(temp.getLoanId(), percent);
        }
        context.put("loanListPercent", loanListMap);
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);

        String accountNo = account.getData().getAccountNo();

        ListResult<TenderOverview> tenderoverview = investservice.findMyTenderOverview(user.getUserId());

        ListResult<StatisticsPaymentPlan> paymentplan = incomeplanservice.findMyPaymentPlan(user.getUserId());
        //最近交易
        ListResult<RecentDeal> recentdeal = dealrecordservice.findMyRecentDeal(accountNo);

        context.put("tenderoverview", tenderoverview);
        context.put("paymentplan", paymentplan);
        context.put("recentdeal", recentdeal);
        context.put("accountNo", accountNo);
        context.put("account", account.getData());
        //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
        String accountMark = account.getData().getAccountMark();
        Double[] accountBalance = { 0.00, 0.00, 0.00 };
        if (accountMark != null && !"".equals(accountMark)) {
            accountBalance = this.doubleDryService.queryBalance(accountMark, "1");
        }
        context.put("accountBacance", accountBalance);
        logger.info("可用余额：" + accountBalance[0]);
        logger.info("可用余额：" + accountBalance[1]);
        logger.info("可用余额：" + accountBalance[2]);
        //站内息
        PageResult<SysMessageInfoDO> pageResult = messageInfoService.queryByUserId(user.getUserId(),
                new PageCondition());
        context.put("MessageCount", pageResult.getTotalCount());

        //查询投资统计，待收汇总
        PlainResult<CashInvesterViewDO> plainResult = cashInvesterService.queryCashInvester(user.getUserId());
        context.put("cashInvesterViewDO", plainResult.getData());
        //未读站内信
        int notReadMessageCount = messageInfoService.queryNotReadMessageCount(user.getUserId());
        context.put("NotReadMessage", notReadMessageCount);
        //查询借款统计,待还汇总
        PlainResult<CashBorrowerViewDO> Cresult = cashBorrowerService.queryCashBorrower(user.getUserId());
        CashBorrowerViewDO cDO = Cresult.getData();
        context.put("cDO", cDO);

        //标的类型加载
        context.put("loanTypes", LoanCategory.values());
    }
}
