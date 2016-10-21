package org.zk.other;

import org.zhangkang.commons.utils.FileUtils;

import java.io.File;
import java.io.InputStream;

/**
 * Created by root on 16-6-10.
 */
public class CompileClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            Process process = Runtime.getRuntime().exec("javac /home/zk/java_workspace/java/javaTest/src/main/java/org/zk/App.java");
            process.waitFor();

            int result = process.exitValue();
            if(result!=0){
                InputStream is = process.getErrorStream();
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                System.out.println(new String(bytes));
            }


            File file = new File("/home/zk/java_workspace/java/javaTest/src/main/java/org/zk/App.class");
            byte[] classBytes = FileUtils.file2Bytes(file);
            Class cls = defineClass("org.zk.App", classBytes, 0, classBytes.length);
            return cls;
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        CompileClassLoader cl = new CompileClassLoader();
        Class cls = cl.loadClass("ss");
        Object app = cls.newInstance();
        System.out.println(cls);
        System.out.println(App.class);
        System.out.println(cls==App.class);
        System.out.println(app.getClass()==cls);
    }
}
