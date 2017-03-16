package org.zk.other.lambda;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class FunctionalInterfaceTest {
    public static void main(String[] args) {
        WorkInterface work = (a) -> {
            System.out.println("do home work!"+a);
        };
        work.doSomeWork(3);

//        new WorkInterface() {
//            @Override
//            public void doSomeWork() {
//                System.out.println("ss");
//            }
//        }.doSomeWork();
    }
}

@FunctionalInterface
interface WorkInterface {
    void doSomeWork(int a);
}
