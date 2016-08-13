package org.zk.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class FinallyWork {

    static int count = 0;

    public static void main(String[] args) {
        while(true){ // 尝试一定的次数，提高程序健壮性
            try{
                if(count++ == 0){
                    throw new OneException("ss");
                }
                System.out.println("no exception");
            }catch (Exception e){
                System.out.println("exception");
            } finally {
                System.out.println("finally");
                if(count==2)
                    break;
            }
        }
    }
}
