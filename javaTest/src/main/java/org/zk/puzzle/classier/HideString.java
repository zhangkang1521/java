package org.zk.puzzle.classier;

/**
 * Created by Administrator on 9/15/2016.
 */
public class HideString {

    // 这个main方法无法执行，本文件中的String将java.lang.String隐藏了
    // 尽量不要这样做
    public static void main(String[] args) {
        System.out.println("hide java.lang.String");
    }
}

/*class String {

}*/
