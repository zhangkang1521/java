package org.zk.framework.action;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * path到Action的映射关系<br>
 */
public class ActionMap {

    private HashMap<String, ActionInfo> map = new HashMap<String, ActionInfo>();

    /**
     * 读取配置文件，存入内存
     */
    public ActionMap() {
        try {
            InputStream in = ActionMap.class.getClassLoader().getResourceAsStream("myStruts.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            //myStruts节点
            Element root = doc.getRootElement();
            //获得actions节点
            Element actions = root.element("actions");
            //获得所有action节点
            List<Element> actionList = actions.elements("action");
            for (Element action : actionList) {
                ActionInfo actionInfo = new ActionInfo();
                String path = action.attributeValue("path");
                String classPath = action.attributeValue("class");
                String method = action.attributeValue("method");
                actionInfo.setClassPath(classPath);
                actionInfo.setMethodName(method);
                map.put(path, actionInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException("读取配置文件出错，请检查配置！", e);
        }
    }

    public ActionInfo get(String path) {
        return map.get(path);
    }

    public static void main(String[] args) throws Exception {
        new ActionMap();
    }
}
