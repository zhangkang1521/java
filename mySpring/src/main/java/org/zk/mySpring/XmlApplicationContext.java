package org.zk.mySpring;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhangkang.commons.utils.StringUtils;

import java.beans.PropertyEditor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现简单springIoc容器功能<br>
 * Created by Administrator on 2016/4/23.
 */
public class XmlApplicationContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(XmlApplicationContext.class);

    /**
     * 存放bean的容器
     */
    private static Map<String, Object> beanContainer = new HashMap<String, Object>();

    /** xml文件中配置的所有bean */
    private List<Element> beanEleList;

    public XmlApplicationContext(String resource) {
        InputStream in = XmlApplicationContext.class.getClassLoader().getResourceAsStream(resource);

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            beanEleList = root.elements("bean");
            initBeans();
            initBeanProperty();
        } catch (Exception e) {
            logger.error("容器初始化错误", e);
        }
    }

    /**
     * 初始化所有bean，放入容器中
     * @throws Exception
     */
    private void initBeans() throws Exception {
        for (Element beanELe : beanEleList) {
            // 实例化对象
            String id = beanELe.attributeValue("id");
            String classPath = beanELe.attributeValue("class");
            Class<?> cls = Class.forName(classPath);
            Object bean = cls.newInstance();
            beanContainer.put(id, bean);
        }
    }

    /**
     * 初始化所有容器的属性
     * @throws Exception
     */
    private void initBeanProperty() throws Exception {
        for (Element beanELe : beanEleList) {
            String id = beanELe.attributeValue("id");
            Object bean = beanContainer.get(id);
            Class cls = bean.getClass();
            //初始化属性
            List<Element> propEleList = beanELe.elements("property");
            for (Element propEle : propEleList) {
                String propName = propEle.attributeValue("name");
                String propValue = propEle.attributeValue("value");
                String propRef = propEle.attributeValue("ref");
                // 判断是依赖还是直接赋值
                if (StringUtils.isNotBlank(propRef)) {
                    Object refBean = beanContainer.get(propRef);
                    Method method = cls.getDeclaredMethod(StringUtils.buildSetMethodName(propName), refBean.getClass());
                    method.invoke(bean, refBean);
                } else {
                    this.invokeSetMethod(bean, propName, propValue);
                }
            }
        }
    }

    /**
     * 执行set方法<br>
     * 此方法会进行查找对应的set方法并执行
     * @param cls
     * @param propName
     * @return
     */
    private boolean invokeSetMethod(Object obj, String propName, String propValue) {
        String methodName = StringUtils.buildSetMethodName(propName);
        Class cls = obj.getClass();
        try {
            Method method = cls.getDeclaredMethod(methodName, String.class);
            method.invoke(obj, propValue);
            return true;
        } catch (Exception e) {
            // nop
        }
        try {
            Method method = cls.getDeclaredMethod(methodName, Integer.class);
            method.invoke(obj, Integer.valueOf(propValue));
            return true;
        } catch (Exception e) {
            // nop
        }
        try {
            Method method = cls.getDeclaredMethod(methodName, Long.class);
            method.invoke(obj, Long.valueOf(propValue));
            return true;
        } catch (Exception e) {
            // nop
        }
        try {
            Method method = cls.getDeclaredMethod(methodName, Float.class);
            method.invoke(obj, Float.valueOf(propValue));
            return true;
        } catch (Exception e) {
            // nop
        }
        try {
            Method method = cls.getDeclaredMethod(methodName, Double.class);
            method.invoke(obj, Double.valueOf(propValue));
            return true;
        } catch (Exception e) {
            // nop
        }
        logger.error("没有找到类{}的{}方法", cls, methodName);
        return false;
    }


    public Object getBean(String name) {
        return beanContainer.get(name);
    }
}
