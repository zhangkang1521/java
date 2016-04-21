package com.autoserve.abc.service.biz.impl.relname;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.testng.annotations.Test;

public class RealNameServiceTest {

    @Test
    public void testQueryList() {
        String xml = "<?xml version='1.0' encoding='UTF-8'?><data><message><status>0</status><value>处理成功</value></message><policeCheckInfos><policeCheckInfo name='吕小东' id='340421199010102098'><message><status>0</status><value>信息认证成功</value></message><name desc='姓名'>吕小东</name><identitycard desc='身份证号'>340421199010102098</identitycard><compStatus desc='比对状态'>0</compStatus><compResult desc='比对结果'>一致</compResult><policeadd desc='原始发证地'>安徽省凤台县</policeadd><checkPhoto desc='照片'>null</checkPhoto><birthday2 desc='出生日期2'>19901010</birthday2><sex2 desc='性别2'>男</sex2></policeCheckInfo></policeCheckInfos><operatResult>处理成功</operatResult></data>";
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            Element weighTime = root.element("message");
            String status = weighTime.elementText("status");
            System.out.println(status);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
