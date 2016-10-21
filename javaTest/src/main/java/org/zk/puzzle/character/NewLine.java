package org.zk.puzzle.character;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Created by Administrator on 10/4/2016.
 */
public class NewLine {
    public static void main(String[] args) throws Exception {
        final String fileName = "E:/test2.txt";
        // 打在控制台，肉眼看起来并没有什么区别，但写到文件中能看出来
      /*  PrintStream ps = new PrintStream(new File(fileName));
        System.setOut(ps);
        for (int i = 0; i < 3; i++) {
            System.out.print(i+"\r\n");
        }*/

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        int c = -1;
        while((c = br.read()) != -1){
            System.out.println(c);
        }
    }
}
