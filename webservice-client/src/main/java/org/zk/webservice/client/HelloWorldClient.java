package org.zk.webservice.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
/**
 * 调用webservice客户端
 * @author zhangkang
 *
 */
public class HelloWorldClient {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		// 以下和服务端安全配置保持一致
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION,
				WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");//用户名随便填
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
				WsClientAuthHandler.class.getName());// 设置密码
		ArrayList list = new ArrayList();
		list.add(new SAAJOutInterceptor());
		list.add(new WSS4JOutInterceptor(outProps));

		JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
		svr.setServiceClass(HelloWorld.class);
		svr.setAddress("http://localhost:8080/webservice/helloWorld");
		// 注入拦截器，用于加密安全验证信息
		svr.getOutInterceptors().addAll(list);

		HelloWorld hw = (HelloWorld) svr.create();

		System.out.println(hw.sayHi("zk"));
	}
}
