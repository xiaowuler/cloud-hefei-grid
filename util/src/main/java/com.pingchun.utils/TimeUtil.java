package com.pingchun.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 格式化时间工具类
 * @author: XW
 * @create: 2019-11-04 16:07
 **/
public class TimeUtil {

    public static Date getTodayEightOClock(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static String CovertDateToString(String timeFormat, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.format(date);
    }

    public static Date CovertStringToDate(String timeFormat, String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.parse(time);
    }

    public static Timestamp getCurrentTime(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date addDay(Date date, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static List<String> getCollectionByTimeRange(Date startTime, Date endTime){
        List<String> collections = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        while (compareMonth(startTime, endTime)){
            collections.add(simpleDateFormat.format(startTime));
            startTime = addMonth(startTime, 1);
        }
        return collections;
    }

    public static Date addMonth(Date date, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    private static boolean compareMonth(Date startTime, Date endTime){
        if (startTime.getYear() > endTime.getYear()){
            return false;
        }else if (startTime.getYear() < endTime.getYear()){
            return true;
        }

        if (startTime.getMonth() > endTime.getMonth()){
            return false;
        }

        return true;
    }

    public static Date getCurrentZeroHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
