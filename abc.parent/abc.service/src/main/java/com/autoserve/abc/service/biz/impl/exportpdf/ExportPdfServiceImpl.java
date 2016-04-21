package com.autoserve.abc.service.biz.impl.exportpdf;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.entity.AgreementMessage;
import com.autoserve.abc.service.biz.entity.ContractBean;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdf;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdfService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class ExportPdfServiceImpl implements ExportPdfService {
    @Resource
    private InvestQueryService     investQueryService;
    @Resource
    private UserService            userService;
    @Resource
    private LoanDao                loanDao;
    @Resource
    private IncomePlanService      incomePlanService;
    @Resource
    private PaymentPlanService     paymentPlanService;
    @Resource
    private CompanyCustomerService companyCustomerService;
    @Resource
    private ExportPdf              exportPdf;
    @Resource
    private SysConfigService       sysConfigService;
    @Resource
    private TransferLoanService    transferLoanSerivce;

    /**
     * 借款合同导出
     * 
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     */
    @Override
    public BaseResult exportBorrowMoney(int loanId, OutputStream out) {
        BaseResult result = new BaseResult();
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.COMMON_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
        //查询投资记录列表
        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }
        //查询标的具体信息
        LoanDO loanDo = loanDao.findById(loanId);
        //查询借款人用户信息
        PlainResult<UserDO> user = userService.findById(loanDo.getLoanUserId());
        //给借款合同赋值
        AgreementMessage agreementMessage = new AgreementMessage();
        agreementMessage.setAgreementNo(loanDo.getLoanNo());
        agreementMessage.setLoanMoney(loanDo.getLoanMoney().toString());
        agreementMessage.setLoanUse(loanDo.getLoanUse());
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = myFormatter.format(loanDo.getLoanExpireDate());
        String startDate = myFormatter.format(loanDo.getLoanFullTransferedtime());
        agreementMessage.setContractEndDate(endDate);
        agreementMessage.setContractStartDate(startDate);
        long days = timeCalculation(startDate, endDate);
        agreementMessage.setContractTerm(days);
        agreementMessage.setSettlementDate(loanDo.getLoanPayDate());
        switch (user.getData().getUserType()) {
            case 1: {
                agreementMessage.setFName(user.getData().getUserRealName());
                agreementMessage.setFAgentName(user.getData().getUserRealName());
                break;
            }
            case 2: {
                agreementMessage.setCompany(true);
                PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(loanDo.getLoanUserId());
                agreementMessage.setFName(enterprise.getData().getCcCompanyName());
                agreementMessage.setCcBusinessLicense(enterprise.getData().getCcLicenseNo());
                agreementMessage.setFAgentName(enterprise.getData().getCcCorporate());
                break;
            }
        }
        agreementMessage.setAnnualInterest(loanDo.getLoanRate().toString());

        agreementMessage.setFAgentUserName(user.getData().getUserName());
        agreementMessage.setFAgentIdCard(user.getData().getUserDocNo());
        // 罚息利率
        PlainResult<SysConfig> platformAccountResult = sysConfigService
                .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
        if (!platformAccountResult.isSuccess() || platformAccountResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "罚息利率查询失败");
        }

        String punitiveInterest = platformAccountResult.getData().getConfValue();
        agreementMessage.setPunitiveInterest(punitiveInterest);

        PlainResult<SysConfig> enterpriseNameResult = sysConfigService.querySysConfig(SysConfigEntry.ENTERPRISE_NAME);
        if (!enterpriseNameResult.isSuccess() || enterpriseNameResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司名称查询失败");
        }

        String enterpriseName = enterpriseNameResult.getData().getConfValue();
        agreementMessage.setSName(enterpriseName);

        PlainResult<SysConfig> enterpriseWebsitResult = sysConfigService
                .querySysConfig(SysConfigEntry.ENTERPRISE_WEBSITE);
        if (!enterpriseWebsitResult.isSuccess() || enterpriseWebsitResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司网址查询失败");
        }
        String enterpriseWebsit = enterpriseWebsitResult.getData().getConfValue();
        agreementMessage.setSWEBSITE(enterpriseWebsit);

        PlainResult<SysConfig> enterpriseAddressResult = sysConfigService
                .querySysConfig(SysConfigEntry.ENTERPRISE_ADDRESS);
        if (!enterpriseAddressResult.isSuccess() || enterpriseAddressResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司地址查询失败");
        }
        String enterpriseAddress = enterpriseAddressResult.getData().getConfValue();
        agreementMessage.setSAddress(enterpriseAddress);
        agreementMessage.setDate(startDate);
        List<Integer> incomeList = new ArrayList<Integer>();
        for (Invest invest : investList) {
            incomeList.add(invest.getUserId());
        }
        ListResult<InvestInformationDO> incomePlanListResult = incomePlanService.findIncomePlanList(loanId, incomeList);
        List<InvestInformationDO> incomePlanList = incomePlanListResult.getData();
        for (int i = 0; i < incomePlanList.size(); i++) {
            incomePlanList.get(i).setDate(String.valueOf(days));
        }
        exportPdf.investBorrowMoney(incomePlanList, agreementMessage, out);

        return BaseResult.SUCCESS;
    }

    /**
     * 债券合同导出
     *
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     * @return
     */
    @Override
    public BaseResult exportObligatoryRight(int loanId, OutputStream out) {
        BaseResult result = new BaseResult();
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
        //查询投资记录列表
        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }
        //查询标的具体信息
        PlainResult<TransferLoan> transferLoanResult = transferLoanSerivce.queryById(loanId);
        TransferLoan transferLoan = transferLoanResult.getData();
        //查询关联标的具体信息
        LoanDO loanDo = loanDao.findById(transferLoan.getOriginId());
        //查询借款人用户信息
        PlainResult<UserDO> user = userService.findById(transferLoan.getUserId());
        //给借款合同赋值
        ContractBean contractBean = new ContractBean();
        contractBean.setContractNo(transferLoan.getTransferLoanNo());
        switch (user.getData().getUserType()) {
            case 1: {
                contractBean.setPartyBName(user.getData().getUserRealName());

                break;
            }
            case 2: {
                PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(transferLoan
                        .getUserId());
                contractBean.setPartyBName(enterprise.getData().getCcCompanyName());
                break;
            }
        }
        contractBean.setPartyBCardNo(user.getData().getUserDocNo());
        contractBean.setPartyBUserName(user.getData().getUserName());
        //年利率
        contractBean.setAnnualInterest(transferLoan.getTransferRate().toString());
        // 罚息利率
        PlainResult<SysConfig> platformAccountResult = sysConfigService
                .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
        if (!platformAccountResult.isSuccess() || platformAccountResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "罚息利率查询失败");
        }

        String punitiveInterest = platformAccountResult.getData().getConfValue();
        contractBean.setPunitiveInterest(punitiveInterest);

        PlainResult<SysConfig> enterpriseNameResult = sysConfigService.querySysConfig(SysConfigEntry.ENTERPRISE_NAME);
        if (!enterpriseNameResult.isSuccess() || enterpriseNameResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司名称查询失败");
        }

        String enterpriseName = enterpriseNameResult.getData().getConfValue();
        contractBean.setPartyCName(enterpriseName);
        PlainResult<SysConfig> enterpriseWebsitResult = sysConfigService
                .querySysConfig(SysConfigEntry.ENTERPRISE_WEBSITE);
        if (!enterpriseWebsitResult.isSuccess() || enterpriseWebsitResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司网址查询失败");
        }
        String enterpriseWebsit = enterpriseWebsitResult.getData().getConfValue();
        contractBean.setPartyCUserName(enterpriseWebsit);
        PlainResult<SysConfig> enterpriseAddressResult = sysConfigService
                .querySysConfig(SysConfigEntry.ENTERPRISE_ADDRESS);
        if (!enterpriseAddressResult.isSuccess() || enterpriseAddressResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "公司地址查询失败");
        }
        String enterpriseAddress = enterpriseAddressResult.getData().getConfValue();
        contractBean.setPartyCCardNo(enterpriseAddress);

        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = myFormatter.format(transferLoan.getFullTransferedtime());
        contractBean.setContractStartDate(startDate);
        String endDate = myFormatter.format(loanDo.getLoanExpireDate());
        long days = timeCalculation(startDate, endDate);
        contractBean.setContractTerm(days);
        contractBean.setSettlementDate(loanDo.getLoanPayDate());
        BigDecimal transferMoney = transferLoan.getTransferMoney();
        contractBean.setContractMoney(transferMoney.toString());
        //查询转让的本金
        PlainResult<BigDecimal> moneyResult = incomePlanService.queryContractMoney(transferLoan.getOriginId(),
                transferLoan.getUserId(), 3);
        BigDecimal contractPrincipal = moneyResult.getData();
        contractBean.setContractPrincipal(contractPrincipal.toString());
        //计算利息
        BigDecimal contractInterest = transferMoney.subtract(contractPrincipal);
        contractBean.setContractInterest(contractInterest.toString());

        List<Integer> incomeList = new ArrayList<Integer>();
        for (Invest invest : investList) {
            incomeList.add(invest.getUserId());
        }
        ListResult<InvestInformationDO> incomePlanListResult = incomePlanService.findIncomePlanList(
                transferLoan.getOriginId(), incomeList);
        List<InvestInformationDO> incomePlanList = incomePlanListResult.getData();
        for (int i = 0; i < incomePlanList.size(); i++) {
            incomePlanList.get(i).setDate(String.valueOf(days));
        }
        exportPdf.exportObligatoryRight(incomePlanList, contractBean, out);

        return result;
    }

    /**
     * 计算开始到结束时间的天数
     * 
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return days
     */
    public long timeCalculation(String startDate, String endDate) {
        long day = 0;

        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = myFormatter.parse(endDate);
            Date mydate = myFormatter.parse(startDate);
            long days = (date.getTime() - mydate.getTime());
            long dayDate = 24 * 60 * 60 * 1000;
            day = days / dayDate;
            System.out.println(day + "毫秒：" + dayDate + "天数：" + days / dayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
