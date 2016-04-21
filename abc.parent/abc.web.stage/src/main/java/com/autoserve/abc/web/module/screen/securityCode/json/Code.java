package com.autoserve.abc.web.module.screen.securityCode.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.util.SecurityCode;
import com.autoserve.abc.service.util.SecurityCode.SecurityCodeLevel;
import com.autoserve.abc.service.util.SecurityImage;

/**
 * 生成验证码
 * @author DS
 *
 * 2015年4月28日上午9:30:31
 */
public class Code {
    private static final Logger logger = LoggerFactory.getLogger(Code.class);
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletResponse response;
    public void execute() {
    	OutputStream out=null;
    	try {
    		response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");
    		out=response.getOutputStream();
    		// 如果开启Hard模式，可以不区分大小写
    		String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false);
    		logger.info("验证码："+securityCode);
    		ByteArrayInputStream imageStream = SecurityImage.getImageAsInputStream(securityCode);
    		//缓存
    		byte[] buffer=new byte[2048];
    		int iSize;
    		while((iSize=imageStream.read(buffer))!=-1){
    			out.write(buffer,0,iSize);
    		}
    		out.flush();
    		// 放入session中
    		session.setAttribute("securityCode", securityCode);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
    }
}
