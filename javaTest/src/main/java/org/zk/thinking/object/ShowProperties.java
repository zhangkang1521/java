package org.zk.thinking.object;

/**
 * Created by Administrator on 9/9/2017.
 */
public class ShowProperties {

    public static void main(String[] args) {
        //System.getProperties().list(System.out);
        // CLASSPATH : IDE不使用环境变量中配置的，命令行使用
        System.out.println(System.getProperty("java.class.path"));
        // Path
        System.out.println(System.getProperty("java.library.path"));
    }
}
