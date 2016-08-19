package org.zk.thinking.exception;

/**
 * Created by Administrator on 8/13/2016.
 */
public class AlwaysFinally {

    public static void main(String[] args) {
        int i=11;
        try{

            try{
                if(i==1)
                    throw new RuntimeException("ss");
                else
                    throw new OneException("one");
            } catch (OneException e){
                System.out.println("inner catch exception");
            }  finally {
                System.out.println("**********");
            }


        }catch (Exception e){
            System.err.println("outer catch exception");
            e.printStackTrace();
        } finally {
            System.err.println("=====================");
        }
    }
}
