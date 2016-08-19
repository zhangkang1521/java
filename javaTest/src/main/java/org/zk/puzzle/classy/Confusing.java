package org.zk.puzzle.classy;

/**
 * Created by Administrator on 8/19/2016.
 */
public class Confusing {

    public Confusing(Object object){
        System.out.println("Confusing(Object)");
    }

    public Confusing(double[] arrays){
        System.out.println("Confusing(double[])");
    }

  /*  public Confusing(int[] array){
        System.out.println("Confusing(int[])");
    }*/

    public static void main(String[] args) {
        // java对于重载的方法，会调用更特定的方法
        // 两个构造函数都可以调用，数组是一个Object,但Object不是一个数组，所以Confusing(double[])更特定
        new Confusing(null);

        new Confusing((Object) null); // 指定调用哪个重载
    }
}
