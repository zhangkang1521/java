/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.tool;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.dao.dataobject.PaymentPlanDO;

/**
 * 类PrintSetters.java的实现描述：TODO 类实现描述
 *
 * @author segen189 2014年11月18日 上午10:56:28
 */
public class PrintSetters {

    public static void printSetMethods(Class<?> clz) {
        Method[] methods = clz.getMethods();

        int intGen = 1; // int 类型参数 递增序号

        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                StringBuilder sb = new StringBuilder();

                sb.append(clz.getSimpleName().substring(0, 1).toLowerCase());
                sb.append(clz.getSimpleName().substring(1));
                sb.append(".");
                sb.append(m.getName());
                sb.append("(");

                if (BigDecimal.class.isAssignableFrom(m.getParameterTypes()[0])) {
                    sb.append("new BigDecimal(" + (intGen++) + ")");
                } else if (Number.class.isAssignableFrom(m.getParameterTypes()[0])) {
                    sb.append(intGen++);
                } else if (String.class.isAssignableFrom(m.getParameterTypes()[0])) {
                    sb.append("\"str" + (intGen++) + "\"");
                } else if (Date.class.isAssignableFrom(m.getParameterTypes()[0])) {
                    sb.append("new Date()");
                } else {
                    sb.append(m.getName().substring(3));
                }

                sb.append(");");

                System.out.println(sb);
            }
        }
    }

    public static void main(String[] args) {
        printSetMethods(PaymentPlanDO.class);
    }

}
