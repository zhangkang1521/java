package org.zk.webservice.server;


import javax.jws.WebService;


@WebService(endpointInterface = "org.zk.webservice.server.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	public String sayHi(String text) {
		return "Hello " + text;
	}

}
