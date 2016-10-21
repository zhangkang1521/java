package org.zk.puzzle.loop;

/**
 * Created by Administrator on 10/3/2016.
 */
public class FloatTest {
    public static void main(String[] args) {
        for(float i=1; i<100; i*=2){
            System.out.println(i+":"+Long.toHexString(Double.doubleToRawLongBits(i)));
        }
        double f = -25.125;
        System.out.println(Long.toHexString(Double.doubleToRawLongBits(f)));
//        int start = 2000000000;
//        int count = 0;
//        float ff = start;
//        System.out.println(Long.toHexString(Double.doubleToRawLongBits(ff)));
//        System.out.println(ff);
//        ff += 50;
//        System.out.println(Long.toHexString(Double.doubleToRawLongBits(ff)));
//        System.out.println(ff);
//        for(float f = start; f < start + 50; f++){
//            count++;
//        }
//        System.out.println(count);
    }
}
