package org.zk.puzzle.exceptional;


/**
 * Created by Administrator on 8/19/2016.
 */
public class Car {
    private static Class engineClass = Engine.class;

    private Engine engine = newEngine();

    // 构造器必须申明抛出实例化属性抛出的异常
//    public Car() throws InstantiationException,IllegalAccessException{
//
//    }

    private static Engine newEngine(){
        try {
            return (Engine) engineClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public Car(){

    }

    public static void main(String[] args) {
        new Car();
    }
}

class Engine {

    private Engine(){

    }

}
