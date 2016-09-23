package org.zk.puzzle.moreLibrary;

/**
 * Created by Administrator on 9/19/2016.
 */
public class Outer {
    B b = new B();

    class B {}

    public class A {}

    public B getB(){
        return b;
    }
}

