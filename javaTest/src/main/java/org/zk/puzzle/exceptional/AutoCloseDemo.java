package org.zk.puzzle.exceptional;

/**
 * Created by Administrator on 8/20/2016.
 */
public class AutoCloseDemo /*implements AutoCloseable*/ {

    public void close() throws Exception {
        System.out.println("close");
    }

    public static void main(String[] args) {

      /*  try(
            AutoCloseDemo closeDemo = new AutoCloseDemo()
        ){
            System.out.println("ss");
        }catch (Exception e){

        }*/
    }
}
