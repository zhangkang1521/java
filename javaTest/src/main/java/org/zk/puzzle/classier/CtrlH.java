package org.zk.puzzle.classier;

/**
 * Created by Administrator on 9/17/2016.
 */
interface A {}
interface B {}
class One {
}

class Two extends One implements A,B{
    public void f(){

    }
}

class Three extends Two {
    public void g(){
        f();
    }
}

public class CtrlH {
    public static void main(String[] args) {
        Two two = new Two();
        two.f();
        two.f();
    }
}
