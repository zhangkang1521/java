package org.zk.thinking.concurrency;

import java.util.concurrent.*;

/**
 * 具有返回值的线程
 * Created by Administrator on 8/14/2016.
 */
class TaskWithResult implements Callable<Integer> {

    private Integer id;
    public TaskWithResult(Integer id){
        this.id = id;
    }

    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return id*2;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newCachedThreadPool();
        // 此处调用submit,不是execute
        Future future = es.submit(new TaskWithResult(3));
        // get方法会阻塞知道结果算出
        System.out.println(future.get());
    }
}
