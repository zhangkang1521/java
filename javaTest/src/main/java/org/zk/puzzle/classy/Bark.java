package org.zk.puzzle.classy;

/**
 * Created by Administrator on 8/20/2016.
 */

class Dog{
    public static void bark(){
        System.out.println("bark");
    }
}

class Bensenji extends Dog{
    // 子类静态方法可以和父类静态方法一致，并不是重写
    public static void bark(){

    }
}

public class Bark {

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.bark();

        Bensenji bensenji = new Bensenji();
        bensenji.bark();// 调用静态方法尽量用类名，消除误导
    }
}
