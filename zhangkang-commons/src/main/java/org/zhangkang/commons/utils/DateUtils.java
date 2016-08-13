package org.zhangkang.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 8/13/2016.
 */
public class DateUtils {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * 获取两个日期之间的天数，去掉时间部分
     * @param date1
     * @param date2
     * @return
     */
    public static int getIntervalDay(Date date1, Date date2){
        Date _date1 = emptyTime(date1);
        Date _date2 = emptyTime(date2);
        return (int)((_date1.getTime()-_date2.getTime())/(1000*60*60*24));
    }

    /*// 使用Calendar设置时间部分的性能好很多，这个方法估计format,parse比较耗时
    public static int getIntervalDay2(Date date1, Date date2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str1 = sdf.format(date1);
        String str2 = sdf.format(date2);
        int interval = 0;
        try {
            Date _date1 = sdf.parse(str1);
            Date _date2 = sdf.parse(str2);
            return (int)((_date1.getTime()-_date2.getTime())/(1000*60*60*24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }*/

    /**
     * 将Date的时间部分置为0
     * @param date
     * @return
     */
    public static Date emptyTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss 的形式
     * @param date
     * @return
     */
    public static String format(Date date){
        return SDF.format(date);
    }
}
