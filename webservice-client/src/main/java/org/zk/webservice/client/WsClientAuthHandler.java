package org.zk.webservice.client;


import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
/**
 * 客户端认证，设置密码
 * @author zhangkang
 *
 */
public class WsClientAuthHandler implements CallbackHandler {
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		pc.setPassword("123456");//和服务器密码保持一致
	}
}
