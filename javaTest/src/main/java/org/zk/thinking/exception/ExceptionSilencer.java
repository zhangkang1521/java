package org.zk.thinking.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class ExceptionSilencer {

    public static int f(int i){
        try {
            if(i==1)
                throw new RuntimeException("xx");
            return 1;
        }finally {
            return 2;
        }
//        return 3;
    }

    public static void main(String[] args) {
        System.out.println(f(1));
//        try{
//            throw new RuntimeException("error");
//        } finally {
//            return;// 不要再finally里面return,覆盖掉了异常信息
//        }
    }
}
