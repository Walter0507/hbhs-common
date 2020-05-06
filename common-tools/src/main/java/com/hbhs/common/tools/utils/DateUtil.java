package com.hbhs.common.tools.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date day(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }
    public static Date week(Date date){
        return week(date, 2);
    }
    public static Date week(Date date, int dayOfWeek){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTime();
    }

    public static Date month(Date date){
        return month(date, 1);
    }
    public static Date month(Date date, int dayOfMonth){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }
}
