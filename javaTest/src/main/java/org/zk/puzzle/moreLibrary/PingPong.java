package org.zk.puzzle.moreLibrary;

/**
 * Created by Administrator on 9/17/2016.
 */
public class PingPong {
    public static synchronized void main(String[] args) {
        Thread thread = new Thread(){
          public void run(){
              pong();
          }
        };
//        thread.run(); // 调用run方法并没有启动线程，是同一线程中，所以立即输出
        thread.start();
        System.out.println("Ping"); // 先获得锁，main线程结束后，释放锁，启动的线程获得锁
    }

    static synchronized void pong(){
        System.out.println(Thread.currentThread());
        System.out.println("Pong");
    }
}
