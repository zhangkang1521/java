package com.autoserve.abc.service.message.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdfService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.message.sms.SendMsgServiceImpl;

public class SendMailServiceImpl implements SendMailService {
    @Resource
    private ExportPdfService        exportPdfService;
    @Resource
    private InvestQueryService      investQueryService;
    @Resource
    private UserService             userService;
    @Resource
    private SimpleMailSenderService simpleMailSender;
    @Resource
    private LoanDao                 loanDao;
    @Resource
    private TransferLoanDao         transferLoanDao;
    @Resource
    private SysConfigService        sysConfigService;
    @Resource
    private CompanyCustomerService  companyCustomerService;

    private static final Logger     logger          = LoggerFactory.getLogger(SendMsgServiceImpl.class);
    private static final Properties emptyProperties = new Properties();

    /**
     *
     */
    private String                  user            = "tiuwer@163.com";
    private String                  host            = "smtp.163.com";
    private String                  password        = "shaoye5334";
    private String                  from            = "tiuwer@qq.com";
    private String                  outFile         = "D:/";

    @Override
    public boolean sendYzm2Mail(String toAddress, String mailContent, String theme) {
        try {
            Session session = Session.getInstance(emptyProperties);
            Message email = new MimeMessage(session);
            // 发件人在配置文件里面配置
            Address fromAddress = new InternetAddress(user, from);
            email.setFrom(fromAddress);
            email.setSubject(theme);
            // 设置邮件的具体内容
            email.setContent(mailContent, "text/html;charset=UTF-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            Address[] addresses = { new InternetAddress(toAddress) };
            email.setRecipients(Message.RecipientType.TO, addresses);
            transport.sendMessage(email, addresses);// 发送给多个人
        } catch (Exception e) {
            logger.info("邮箱发送失败！", e);
            return false;
        }
        return true;
    }

    @Override
    public BaseResult sendMailToCreditoUser(int loanId, String loanName) {
        BaseResult result = new BaseResult();
        OutputStream out;
        String contractPath = null;
        try {
            String dir = outFile + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;

            // 判断目录是否存在，不存在创建
            File tempFile = new File(dir);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }

            contractPath = dir + loanName + ".pdf";
            out = new FileOutputStream(contractPath);
            BaseResult exportResu = exportPdfService.exportObligatoryRight(loanId, out);
            if (exportResu.isSuccess()) {
                transferLoanDao.updateContractPath(loanId, contractPath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 设置给投资人发送邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(host);
        mailInfo.setMailServerPort("587");
        mailInfo.setValidate(true);
        mailInfo.setUserName(user);
        mailInfo.setPassword(password);// 您的邮箱密码
        mailInfo.setFromAddress(from);
        StringBuffer toList = new StringBuffer();
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }
        List<Integer> userIdList = new ArrayList<Integer>();
        for (Invest invest : investList) {
            userIdList.add(invest.getUserId());
        }
        ListResult<UserDO> userListResult = userService.findByList(userIdList);
        List<UserDO> userList = userListResult.getData();
        if (!userListResult.isSuccess() || CollectionUtils.isEmpty(userList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户信息查询失败");
        }
        List<String> mailList = new ArrayList<String>();
        for (UserDO userDO : userList) {
            mailList.add(userDO.getUserEmail());
        }
        HashSet<String> mailSet = new HashSet<String>(mailList);
        Iterator<String> iterator = mailSet.iterator();
        mailList = new ArrayList<String>();
        while (iterator.hasNext()) {
            mailList.add(iterator.next());
        }
        if (mailList.size() < 2) {
            toList.append(mailList.get(0));
        } else {
            for (int i = 0; i < userList.size(); i++) {
                toList.append(mailList.get(i));
                if (i != (mailList.size() - 1)) {
                    toList.append(",");
                }
            }
        }
        mailInfo.setToAddress(toList.toString());
        mailInfo.setSubject("给债权受让人");
        mailInfo.setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）受让的债权已经成功买入，具体明细请通过网站-“我的账户”内相关信息进行查看。<br> 若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）   <br>新华久久贷 ");
        // 给投资人发送邮件
        result = simpleMailSender.sendHtmlMail(mailInfo, contractPath);// 发送html格式
        // 设置给借款人发送邮件
        MailSenderInfo borrowMailInfo = new MailSenderInfo();
        borrowMailInfo.setMailServerHost(host);
        borrowMailInfo.setMailServerPort("587");
        borrowMailInfo.setValidate(true);
        borrowMailInfo.setUserName(user);
        borrowMailInfo.setPassword(password);// 您的邮箱密码
        borrowMailInfo.setFromAddress(from);
        StringBuffer borrowUserMaile = new StringBuffer();
        TransferLoanDO loanDo = transferLoanDao.findById(loanId);
        PlainResult<UserDO> userDo = userService.findById(loanDo.getTlUserId());
        borrowUserMaile = borrowUserMaile.append(userDo.getData().getUserEmail());
        borrowMailInfo.setToAddress(borrowUserMaile.toString());
        borrowMailInfo.setSubject("给债权出让人");
        borrowMailInfo
                .setContent("尊敬的客户：<br>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷（www.xh99d.com）转让的债权已成功转出，回款资金已转入您的乾多多账户。具体明细请查看网站-“我的账户”内相关信息。<br>若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：债权转让合同）<br>新华久久贷");
        result = simpleMailSender.sendHtmlMail(borrowMailInfo, contractPath);// 发送html格式
        return result;
    }

    @Override
    public BaseResult sendMailToInvestUser(int loanId, String loanName) {
        BaseResult result = new BaseResult();
        OutputStream out;
        String contractPath = null;
        try {
            String dir = outFile + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;

            // 判断目录是否存在，不存在创建
            File tempFile = new File(dir);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }

            contractPath = dir + loanName + ".pdf";

            out = new FileOutputStream(contractPath);
            BaseResult exportResu = exportPdfService.exportBorrowMoney(loanId, out);
            if (exportResu.isSuccess()) {
                // 更新数据库
                loanDao.updateContractPath(loanId, contractPath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 设置给投资人发送邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(host);
        mailInfo.setMailServerPort("587");
        mailInfo.setValidate(true);
        mailInfo.setUserName(user);
        mailInfo.setPassword(password);// 您的邮箱密码
        mailInfo.setFromAddress(from);
        StringBuffer toList = new StringBuffer();
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.COMMON_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }
        List<Integer> userIdList = new ArrayList<Integer>();
        for (Invest invest : investList) {
            userIdList.add(invest.getUserId());
        }
        ListResult<UserDO> userListResult = userService.findByList(userIdList);
        List<UserDO> userList = userListResult.getData();
        if (!userListResult.isSuccess() || CollectionUtils.isEmpty(userList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户信息查询失败");
        }
        Set<String> mailSet = new HashSet<String>();
        for (UserDO userDO : userList) {
            mailSet.add(userDO.getUserEmail());
        }
        Iterator<String> it = mailSet.iterator();
        while (it.hasNext()) {
            toList.append(it.next());
            if (it.hasNext()) {
                toList.append(",");
            }
        }
        mailInfo.setToAddress(toList.toString());

        mailInfo.setSubject("给投资人的邮件");
        mailInfo.setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您在新华久久贷投资的项目已经成交并开始计息，收益以实际投资期限为准，具体明细请查看新华久久贷网站（www.xh99d.com）-“我的账户”内相关信息。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：投资合同）  <br/>新华久久贷 ");

        // 给投资人发送邮件，改为每个人单独发送
        //result = simpleMailSender.sendHtmlMail(mailInfo, contractPath);
        new SendMailThread(mailInfo, contractPath, simpleMailSender).start();
        logger.debug("投资人email:" + mailInfo.getToAddress());
        // 设置给借款人发送邮件
        MailSenderInfo borrowMailInfo = new MailSenderInfo();
        borrowMailInfo.setMailServerHost(host);
        borrowMailInfo.setMailServerPort("587");
        borrowMailInfo.setValidate(true);
        borrowMailInfo.setUserName(user);
        borrowMailInfo.setPassword(password);// 您的邮箱密码
        borrowMailInfo.setFromAddress(from);
        LoanDO loanDo = loanDao.findByLoanId(loanId);
        PlainResult<UserDO> userDo = userService.findById(loanDo.getLoanUserId());
        //企业用户邮箱保存在abc_company_customer中
        if (UserType.ENTERPRISE.getType() == userDo.getData().getUserType()) {
            PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(loanDo.getLoanUserId());
            borrowMailInfo.setToAddress(enterprise.getData().getCcContactEmail());
        } else if (UserType.PERSONAL.getType() == userDo.getData().getUserType()) {
            borrowMailInfo.setToAddress(userDo.getData().getUserEmail());
        }
        borrowMailInfo.setSubject("给借款人的邮件");
        borrowMailInfo
                .setContent("尊敬的客户：<br/>您好，感谢您对新华久久贷的信任与支持。您通过新华久久贷网站（www.xh99d.com）融资项目已经完成融资，所筹集款项已转入您的乾多多账户，请您查收并可以提现。具体还款明细可通过网站-“我的账户”-“我的借款”-“还款计划”查询，请按时还款。若有疑问可咨询网站在线客服，也可电话联系网站客服。（附件：借款合同）<br/>新华久久贷");

        // result = simpleMailSender.sendHtmlMail(borrowMailInfo, contractPath);// 发送html格式
        new SendMailThread(borrowMailInfo, contractPath, simpleMailSender).start();
        logger.debug("借款人email:" + borrowMailInfo.getToAddress());
        return result;
    }

    /**
     * 发送邮件线程类
     * 
     * @author zhangkang 2015年6月11日 下午5:36:35
     */
    static class SendMailThread extends Thread {
        private MailSenderInfo          mailInfo;
        private String                  contractPath;
        private SimpleMailSenderService simpleMailSender;

        public SendMailThread(MailSenderInfo mailInfo, String contractPath, SimpleMailSenderService simpleMailSender) {
            this.mailInfo = mailInfo;
            this.contractPath = contractPath;
            this.simpleMailSender = simpleMailSender;
        }

        @Override
        public void run() {
            String[] receivers = mailInfo.getToAddress().split(",");
            //邮件分开发送
            for (String receive : receivers) {
                mailInfo.setToAddress(receive);
                simpleMailSender.sendHtmlMail(mailInfo, contractPath);// 发送html格式
            }
        }
    }

    @Override
    public BaseResult sendMailToManager(LoanIntentApply loanIntentApply) {
        BaseResult result = new BaseResult();
        String toAddress = "";

        List<String> list = new ArrayList<String>();
        list.add("MAIL_SMTP_SERVER");
        list.add("MAIL_PORT");
        list.add("MAIL_ADDRESS");
        list.add("MAIL_PASSWORD");
        list.add("MAIL_SENDER_NAME");

        // 设置给平台管理者发送邮件，取出系统配置的邮件参数
        ListResult<SysConfig> mailConf = sysConfigService.queryListByParam(list);

        MailSenderInfo mailInfo = new MailSenderInfo();

        for (SysConfig SysConfig : mailConf.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.MAIL_SMTP_SERVER) {
                mailInfo.setMailServerHost(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PORT) {
                mailInfo.setMailServerPort(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_ADDRESS) {
                toAddress = SysConfig.getConfValue();
                mailInfo.setFromAddress(SysConfig.getConfValue());
                mailInfo.setUserName(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PASSWORD) {
                mailInfo.setPassword(SysConfig.getConfValue());
            }
        }
        mailInfo.setValidate(true);
        mailInfo.setToAddress(toAddress);

        mailInfo.setSubject("给新华久久贷平台管理者的邮件");
        mailInfo.setContent("尊敬的平台管理者：<br/>您好，现有一条新的前台借款申请<br/><br/>产品类型 ："
                + loanIntentApply.getIntentState().getPrompt() + "<br/><br/>融资金额 ：" + loanIntentApply.getIntentMoney()
                + "<br/><br/>借款人名称 ：" + loanIntentApply.getUserName() + "<br/><br/>手机号码 ：" + loanIntentApply.getPhone()
                + "<br/><br/>详细信息请您登陆新华久久贷后台在  融资意向管理-意向审核  中查看  <br/>新华久久贷 ");

        // 给投资人发送邮件
        result = simpleMailSender.sendTextMail(mailInfo);// 发送h格式
        return result;
    }

    @Override
    public BaseResult sendMail(String toAddress, String content, String subject) {
        BaseResult result = new BaseResult();

        List<String> list = new ArrayList<String>();
        list.add("MAIL_SMTP_SERVER");
        list.add("MAIL_PORT");
        list.add("MAIL_ADDRESS");
        list.add("MAIL_PASSWORD");
        list.add("MAIL_SENDER_NAME");

        // 设置给平台管理者发送邮件，取出系统配置的邮件参数
        ListResult<SysConfig> mailConf = sysConfigService.queryListByParam(list);

        MailSenderInfo mailInfo = new MailSenderInfo();

        for (SysConfig SysConfig : mailConf.getData()) {
            if (SysConfig.getConf() == SysConfigEntry.MAIL_SMTP_SERVER) {
                mailInfo.setMailServerHost(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PORT) {
                mailInfo.setMailServerPort(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_ADDRESS) {
                mailInfo.setFromAddress(SysConfig.getConfValue());
                mailInfo.setUserName(SysConfig.getConfValue());
            }
            if (SysConfig.getConf() == SysConfigEntry.MAIL_PASSWORD) {
                mailInfo.setPassword(SysConfig.getConfValue());
            }
        }
        mailInfo.setValidate(true);
        mailInfo.setToAddress(toAddress);

        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        result = simpleMailSender.sendHTMLMail(mailInfo);// 发送html格式
        return result;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
