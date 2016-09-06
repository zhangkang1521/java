package org.zk.puzzle.classy;

/**
 * Created by zhangkang on 2016/9/6.
 */
public class Thing {
    private int i;

    public Thing(){

    }

    public Thing(int i){
        this.i = i;
    }

    public static void main(String[] args) {
        new MyThing();
    }

}

class MyThing extends Thing {

    private int arg;

    public MyThing(){
        //super(arg = OtherClass.getValue()); //compile error
        this(OtherClass.getValue());
    }

    private MyThing(int i){
        super(i);
        arg = i;
    }
}

class OtherClass {
    public static int getValue(){
        return  100;
    }
}
