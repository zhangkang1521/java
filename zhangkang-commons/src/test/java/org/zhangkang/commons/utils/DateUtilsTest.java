package org.zhangkang.commons.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 8/13/2016.
 */
public class DateUtilsTest {

    @Test
    public void testIntervalDay(){
        for(int i=0; i<10000; i++) {
            Date now = new Date();

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(now);
            cal1.set(Calendar.DAY_OF_MONTH, 2);
            cal1.set(Calendar.HOUR_OF_DAY, 22);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(now);
            cal2.set(Calendar.DAY_OF_MONTH, 1);
            cal2.set(Calendar.HOUR_OF_DAY, 18);


            int day = DateUtils.getIntervalDay(cal1.getTime(), cal2.getTime());
//            System.out.println(day);
        }
    }

    @Test
    public void testFormat(){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        System.out.println(DateUtils.format(cal1.getTime()));
    }
}
