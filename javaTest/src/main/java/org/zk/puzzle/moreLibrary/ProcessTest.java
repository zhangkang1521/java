package org.zk.puzzle.moreLibrary;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Process;

/**
 * Created by Administrator on 9/21/2016.
 */
public class ProcessTest {


    static void drainInBackground(final InputStream in){
        new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                try {
                    String line = null;
                    while((line = br.readLine()) != null){
                        System.out.println(line);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws Exception{
        //System.out.println(System.getProperty("os.name"));

        String[] cmd = new String[]{"cmd.exe", "/C", "echo hello,exec"};
        // String[] cmd = new String[]{"/bin/sh", "-c", "echo hello,exec"};
        Process process = Runtime.getRuntime().exec(cmd);
        drainInBackground(process.getInputStream()); // 必须把这个流放干，否则一直等待
        drainInBackground(process.getErrorStream());
        int exitValue = process.waitFor();
        System.out.println(exitValue);

    }
}
