package org.zk.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface HelloWorld {
	
	@WebMethod
	String sayHi(@WebParam(name = "text") String text);
}
