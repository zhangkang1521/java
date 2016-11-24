package org.zk.other.task;

import org.joda.time.DateTime;

/**
 * Created by Administrator on 11/8/2016.
 */
public class DateTimeTest {
    public static void main(String[] args) {
        DateTime d1 = new DateTime();
        DateTime d2 = d1.plusSeconds(10);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println((d2.toDate().getTime()-d1.toDate().getTime())/1000);
    }
}
