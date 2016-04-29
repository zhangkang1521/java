package org.zk.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 定义服务端接口
 * 需指定targetNamespace,且必须和服务器包路径保持一致
 * @author zhangkang
 *
 */
@WebService(targetNamespace = "http://server.webservice.zk.org/")
public interface HelloWorld {
	
	@WebMethod
	String sayHi(@WebParam(name = "text") String text);
}
