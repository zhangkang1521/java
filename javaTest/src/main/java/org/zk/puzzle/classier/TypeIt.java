package org.zk.puzzle.classier;

import org.zk.puzzle.classier.click.CodeTalk;

/**
 * Created by Administrator on 9/15/2016.
 */
public class TypeIt {


    public static void main(String[] args) {
        ClickIt clickIt = new ClickIt();
        clickIt.doIt();
    }
}

class ClickIt extends CodeTalk {
    // 并没有重写 CodeTalk的printMessage方法
    // 这里看不到CodeTalk的printMessage方法
    void printMessage(){
        System.out.println("hack");
    }
}
