package org.zk.puzzle.moreLibrary;

import java.math.BigDecimal;

/**
 * Created by Administrator on 9/19/2016.
 */
public class DogSleep {

    public void eat() {
        System.out.println("eat");
    }

    public void sleep() {
        System.out.println("sleep");
    }

    public void live() {
        new Thread() {
            DogSleep dogSleep = new DogSleep();

            public void run() {
                eat();
                // 只能看到继承自Thread的sleep(long)
                //sleep();
            }
        }.start();
    }

    public void live2() {
        new Thread(new Runnable() {
            public void run() {
                DogSleep dogSleep = new DogSleep();
                eat();
                sleep();
                System.out.println(Thread.currentThread());
            }
        }).start();
    }


    public static void main(String[] args) {
        new DogSleep().live2();
    }
}
