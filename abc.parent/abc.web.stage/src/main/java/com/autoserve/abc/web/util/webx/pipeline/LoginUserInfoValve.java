package com.autoserve.abc.web.util.webx.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.QueryStringBuilder;

/**
 * 获取登录用户信息
 */
public class LoginUserInfoValve extends AbstractValve {

    private static final Logger log = LoggerFactory.getLogger(LoginUserInfoValve.class);

    @Autowired
    private UserService         userService;

    @Autowired
    private RoleService         roleService;

    @Autowired
    private DeployConfigService deployConfigService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @Autowired
    private HttpSession         session;
    @Autowired
    private SysConfigService  	sysConfigService;

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
	        try {
	        	TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
	        	//查询站点是否关闭
	        	PlainResult<SysConfig> resultSysConfig =sysConfigService.querySysConfig(SysConfigEntry.SHUTDOWN_SITE);
	        	SysConfig sysConfig=resultSysConfig.getData();
	        	if(sysConfig!=null && "1".equals(sysConfig.getConfValue()) && 
	        			!URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/offSite/")){
	        		PlainResult<SysConfig> sysConfigInfo =sysConfigService.querySysConfig(SysConfigEntry.SHUTDOWN_INFO);
	        		rundata.getContext().put("shutdownInfo", sysConfigInfo.getData().getConfValue());
	        		rundata.forwardTo("/offSite").end();
	        	}else{
		        	//我的账户、我要借款
		            if(URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/account/") ||
		               URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/apply/pledgeApply") ||
		               URLDecoder.decode(request.getRequestURI(), "utf-8").startsWith("/apply/securedApply")){
//		                TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
		
		                final String loginUrl = deployConfigService.getLoginUrl(request)+"?redirectUrl="
		                        + URLEncoder.encode(getLocaleUrl(rundata), "utf-8");
		
		                // 1 校验token是否有效。token由以下字段加密组成：用户id, 有效期至的时间戳, 签名
		                User user = (User) session.getAttribute("user");
		
		                // 1.1 校验不通过，跳转登录页面
		                if (user == null || user.getUserId() == null) {
		                    //rundata.redirectToLocation(loginUrl);
		                    rundata.setRedirectLocation(loginUrl);
		                }
		                // 1.2 校验通过
		                // 取用户信息保存到LoginUserInfoHelper
		                // 并更新cookie的登录信息
	//	                else {
	//	                    PlainResult<UserDO> userFindResult = userService.findById(user.getUserId());
	//	                    if (!userFindResult.isSuccess()) {
	//	                        log.warn("用户查询失败", userFindResult.getMessage());
	//	                        rundata.redirectToLocation(loginUrl);
	//	                    } else {
	//	                        UserDO userDO = userFindResult.getData();
	//	                        LoginUserInfo loginUserInfo = new LoginUserInfo();
	//	
	//	                        loginUserInfo.setUserId(userDO.getUserId());
	//	                        loginUserInfo.setUserEmail(userDO.getUserEmail());
	//	                        loginUserInfo.setUserPhone(userDO.getUserPhone());
	//	                        loginUserInfo.setUserName(userDO.getUserName());
	//	                        loginUserInfo.setUserHeadImg(userDO.getUserHeadImg());
	//	                        loginUserInfo.setLoginIp(IPUtil.getUserIpAddr(request));
	//	
	//	                        LoginUserInfoHelper.setLoginUserInfo(loginUserInfo);
	//	
		                    }
		                }
	        } catch (Exception e) {
	            log.warn(e.getMessage(), e);
	        } finally {
	            pipelineContext.invokeNext();
	        }
    }

    /**
     * 获取当前url
     */
    private String getLocaleUrl(TurbineRunDataInternal rundata) {
        // 获取当前url根路径，如 http://abc.aliyun-inc.net
        String curScheme = rundata.getRequest().getScheme(); // http
        String curServerName = rundata.getRequest().getServerName(); // abc.aliyun-inc.net
        int curServerPort = rundata.getRequest().getServerPort();
        String curUri = rundata.getRequest().getRequestURI();
        String curQueryString = rundata.getRequest().getQueryString();

        String curlocaleUrlRoot = curScheme + "://" + curServerName;
        if (curServerPort != 80) {
            curlocaleUrlRoot += ":" + curServerPort;
        }

        String localeUrl = curlocaleUrlRoot;
        if (StringUtils.isNotBlank(curUri)) {
            localeUrl = localeUrl + curUri;
        }

        if (StringUtils.isNotBlank(curQueryString)) {
            Map<String, String> queryPairs = splitQuery(curQueryString);
            QueryStringBuilder queryStrBuilder = new QueryStringBuilder();
            for (String key : queryPairs.keySet()) {
                queryStrBuilder.addQueryParameter(key, queryPairs.get(key));
            }

            try {
                localeUrl = localeUrl + queryStrBuilder.encode("UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }

        return localeUrl;
    }

    private Map<String, String> splitQuery(String queryString) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        try {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }

            return queryPairs;
        } catch (Exception e) {
            return queryPairs;
        }
    }

}
