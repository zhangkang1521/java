package org.zk.puzzle.library;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 9/10/2016.
 */
public class Creation {

    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 100; i++) {
//            new Creature();
//        }
        ExecutorService es = Executors.newFixedThreadPool(10);
        es.execute(new CreateThread());
        es.execute(new CreateThread());
        es.execute(new CreateThread());
        es.execute(new CreateThread());
        es.execute(new CreateThread());
        es.shutdown();
        Thread.sleep(1000);
        System.out.println("===" + Creature.getNumCreated()); // 不太对
    }
}

class CreateThread implements Runnable {

    @Override
    public void run() {
        new Creature2();
    }
}

class Creature {
    private static long numCreated = 0;

    public Creature() {
        numCreated++;
        System.out.println(numCreated);
    }

    public static long getNumCreated() {
        return numCreated;
    }
}

class Creature2 {
    private static long numCreated = 0;

    public Creature2() {
        synchronized (Creature2.class) {
            numCreated++;
            System.out.println(numCreated);
        }
    }

    public static long getNumCreated() {
        return numCreated;
    }
}

class Creature3 {
    private static AtomicLong numCreated = new AtomicLong();

    public Creature3() {
        System.out.println(numCreated.incrementAndGet());
        //System.out.println(numCreated); 这里输出已经不安全了
    }

    public static AtomicLong getNumCreated() {
        return numCreated;
    }
}




