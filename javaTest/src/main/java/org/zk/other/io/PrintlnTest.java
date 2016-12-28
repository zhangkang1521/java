package org.zk.other.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

/**
 * Created by Administrator on 12/26/2016.
 */
public class PrintlnTest {
    public static void main(String[] args) throws Exception{
//        PrintStream ps = new PrintStream(new File("E:/tmp/windowns.txt"));
//        ps.println("aaa");
//        ps.println("bbb");
        FileInputStream in = new FileInputStream("E:/tmp/windowns.txt");

        int data = -1;
        while((data = in.read()) != -1){
            System.out.print(data + " ");
        }
    }
}
