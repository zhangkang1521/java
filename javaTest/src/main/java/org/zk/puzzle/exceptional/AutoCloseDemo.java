package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/20/2016.
 */
public class AutoCloseDemo implements AutoCloseable {

    public void close() throws Exception {
        System.out.println("close");
    }

    public static void main(String[] args) {

        try(
            AutoCloseDemo closeDemo = new AutoCloseDemo()
        ){
            System.out.println("ss");
            int a = 0;
            a = 5/a;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
