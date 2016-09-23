package org.zk.puzzle.classier;

/**
 * Created by Administrator on 9/15/2016.
 */
class Base {
    // final 修饰变量，此变量不被赋值两次
    public static final String PRICE = "$64.000";
    public String className = "Base";

    public static void hide(){
        System.out.println("base hide");
    }

    // final 修饰实例方法，此方法不能重写
    public final void instanceMethod(){
        System.out.println("Base.instanceMetod()");
    }

    // final 修饰静态方法，此方法不能被隐藏
    public final static void staticMethod(){

    }
}

class Derived extends Base{

    public static final String PRICE = "2 cents";

    // 名字和父类名字一样，隐藏了父类的属性，并不是重写
    // static 方法也是一样，应尽量避免这种情况
    private String className = "Derived";

    // TODO 为什么不能为 private
   /* private static void hide(){
        System.out.println("derived hide");
    }*/

    /*public void instanceMethod(){

    }*/

    /*public static void staticMethod(){

    }*/
}
public class HideProperties {
    public static void main(String[] args) {
        //System.out.println(new Derived().className);
//        Base base = new Base();
//        Base derived = new Derived();
//        base.hide();
//        derived.hide();
        System.out.println(((Base)new Derived()).className);

        System.out.println(Derived.PRICE);
    }
}
