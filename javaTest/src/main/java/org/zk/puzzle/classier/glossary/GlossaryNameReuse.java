package org.zk.puzzle.classier.glossary;

/**
 * Created by Administrator on 9/17/2016.
 */
public class GlossaryNameReuse {
    static String sentence = "I don't know";

    public static void main(String[] args) {
        String sentence = "I know"; // shadows static field
        System.out.println(sentence);
    }
}

class Base {
    public void f1(){}
    public static void f2(){}
}

class Derived extends Base{
    public void f1() {} // overrides Base.f1()
    public static void f2(){} // hides Base.f2()
}

class Belt {
    private final int size;

    public Belt(int size){ // parameter shadows Belt.size
        this.size = size;
    }
}

class Obscure {
    static String System;

    public static void main(String[] args) {
        //System.out.println("ss"); //
    }
}

