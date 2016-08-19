package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/19/2016.
 */
public class HelloGoodBye {

    public static void main(String[] args) {
//        try{
//            System.out.println("Hello,World");
//            System.exit(1); //
//        } finally {
//            System.out.println("Goodbye world");
//        }

        System.out.println("Hello,world");

        // 虚拟机退出前回调
//        Runtime.getRuntime().addShutdownHook(new Thread(){
//            @Override
//            public void run() {
//                System.out.println("bye!");
//            }
//        });
//        System.exit(1);
    }
}
