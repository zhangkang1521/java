package com.autoserve.abc.service.message.sms;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.util.Md5Util;

public class SendMsgServiceImpl implements SendMsgService {

    private static final Logger logger = LoggerFactory.getLogger(SendMsgServiceImpl.class);

    private String              messageAccount;
    private String              messagePassword;
    private String              soapObjectNamespace;
    private String              soapObjectName;
    private String              androidHttpTransportURL;
    private String              transportCallURL;

    @Override
    public boolean sendMsg(String telephone, String content, String personName,String messageTypeId) {
        String md5pass = Md5Util.md5(messagePassword);
        SoapObject request = new SoapObject(soapObjectNamespace, soapObjectName);
        request.addProperty("orgAccount", messageAccount);
        request.addProperty("orgAccountPwd", md5pass);
        request.addProperty("messageTypeId", messageTypeId);
        request.addProperty("content", content);
        request.addProperty("mobile", telephone);
        request.addProperty("personName", personName);
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        AndroidHttpTransport transport = new AndroidHttpTransport(androidHttpTransportURL);
        transport.debug = true;
        // 注册Envelope
        (new MarshalBase64()).register(envelope);
        // 调用WebService5
        try {
            transport.call(transportCallURL, envelope);
            Object result=envelope.getResponse();

            if (logger.isInfoEnabled()) {
                logger.info(String.valueOf(request.getProperty(3)));
                logger.info(String.valueOf(result));
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("短信发送失败!");
            return false;
        }
        return true;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public void setSoapObjectNamespace(String soapObjectNamespace) {
        this.soapObjectNamespace = soapObjectNamespace;
    }

    public void setSoapObjectName(String soapObjectName) {
        this.soapObjectName = soapObjectName;
    }

    public void setAndroidHttpTransportURL(String androidHttpTransportURL) {
        this.androidHttpTransportURL = androidHttpTransportURL;
    }

    public void setTransportCallURL(String transportCallURL) {
        this.transportCallURL = transportCallURL;
    }

}
