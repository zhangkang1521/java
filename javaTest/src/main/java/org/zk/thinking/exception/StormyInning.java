package org.zk.thinking.exception;


class BaseBallException extends Exception {

}
class Foul extends BaseBallException {

}
class Strike extends BaseBallException {

}

class StormException extends Exception {

}
class RainedOut extends StormException {

}
class PopFoul extends Foul {

}

abstract class Inning {
    public Inning() throws BaseBallException {

    }

    public abstract void atBat() throws Strike,Foul;

    public void event() throws BaseBallException{

    }

}


interface Storm {
    public void event() throws RainedOut,BaseBallException;
}

public class StormyInning extends Inning implements Storm{

    // 对于构造器，子类必须抛出父类的异常，可以抛出比父类构造器更多的异常
    public StormyInning() throws BaseBallException,RainedOut{

    }

    // 子类方法不能抛出比父类方法更多的异常,可以抛出比父类方法更少的异常
    // 也可以抛出父类方法异常的子类
    public void atBat() throws Strike,PopFoul {

    }

    // 此方法不能抛出比父类更多的异常 && 不能抛出比接口更多的异常
    // 理解：如果用父类对象调用这个方法，肯定不会处理接口的异常
    public void event(){

    }
}
