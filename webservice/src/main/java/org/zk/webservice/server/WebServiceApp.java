package org.zk.webservice.server;


import javax.xml.ws.Endpoint;

/**
 * webservice启动测试
 * @author zhangkang
 *
 */
public class WebServiceApp {
	public static void main(String[] args) {
		System.out.println("web service start");
		HelloWorldImpl implementor = new HelloWorldImpl();
		String address = "http://localhost:8081/webservice/helloWorld";
		Endpoint.publish(address, implementor);
		System.out.println("web service started");
	}
}
