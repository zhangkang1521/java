package org.zk.puzzle.classier;
import static java.util.Arrays.toString;


/**
 * Created by Administrator on 9/15/2016.
 */
public class ImportDuty {

    public static void main(String[] args) {
        printArgs(1, 2, 3);
    }


    static void printArgs(Object... args){
        // 调用的是 Object.toString，不会使用静态导入的Arrays.toString
        //System.out.println(toString(args));
    }
}
