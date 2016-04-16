package org.zk.framework.action;

/**
 * 封装路径对应的类和方法
 */
public class ActionInfo {
    /**类的全路径*/
    private String classPath;
    /**方法名*/
    private String methodName;

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
