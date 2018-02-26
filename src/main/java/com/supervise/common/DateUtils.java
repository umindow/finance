package com.supervise.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by xishui.hb on 2018/2/12 下午4:23.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public final class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date parseStringDate(String date,String pattern){
        String pt = (null == pattern) ? DEFAULT_PATTERN:pattern;
        SimpleDateFormat format = new SimpleDateFormat(pt);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String formatDate(Date date, String pattern) {
        return formatDate(date, pattern, Locale.ENGLISH);
    }

    public static String formatDate(Date date, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(date);
    }

    public static Date String2Date(String dateString, String pattern) {
        return String2Date(dateString, pattern, Locale.ENGLISH);
    }

    public static Date String2Date(String dateString, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        Date date = null;

        try {
            date = sdf.parse(dateString);
            
        } catch (ParseException var6) {
           // throw new Exception("_003_00_00_00003");
        	return null;
        }
        return date;
    }

    public static Date addDays(Date date, int days) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(5, days);
        return cd.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(12, minutes);
        return cd.getTime();
    }

    public static Date addMillisecond(Date date, int millisecond) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(14, millisecond);
        return cd.getTime();
    }

    public static Date getDayByTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static int differenceOfTwoDate(Date date1, Date date2) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date1);
        fromCalendar.set(11, 0);
        fromCalendar.set(12, 0);
        fromCalendar.set(13, 0);
        fromCalendar.set(14, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date2);
        toCalendar.set(11, 0);
        toCalendar.set(12, 0);
        toCalendar.set(13, 0);
        toCalendar.set(14, 0);
        long days = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / 86400000L;
        return (new Long(days)).intValue();
    }

    public static Date adjustDateByFreq(Date date, String frequency, int direction) {
        if(direction == 0) {
            return date;
        } else {
            int adjustDay = direction > 0?1:-1;
            int maxCount = 7;

            for(int weekday = getWeekDay(date); frequency.indexOf(String.valueOf(weekday)) < 0 && maxCount > 0; --maxCount) {
                date = addDays(date, adjustDay);
                weekday = getWeekDay(date);
            }

            return date;
        }
    }

    public static int getWeekDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        int weekDay = cal.get(7) - 1;
        return weekDay == 0?7:weekDay;
    }

  
    public static boolean isDayWithinFrequency(Date date, String frequency) {
        int weekday = getWeekDay(date);
        return frequency.indexOf(String.valueOf(weekday)) >= 0;
    }

    public static Date nextDay(Date date) {
        return addDays(date, 1);
    }

    public static Date previousDay(Date date) {
        return addDays(date, -1);
    }

 
    public static int countDaysInMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(5);
    }
    
}
