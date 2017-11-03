package com.risfond.rnss.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Abbott on 2017/6/2.
 */

public class ChatTime {

    /**
     * 时间戳格式转换
     */
    static String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

    /**
     * 格式化聊天时间
     * @param msgTime
     * @param localTime
     * @return
     */
    public static String getChatTime(long msgTime, long localTime) {
        Date date_before = new Date(msgTime);
        Date date_now = new Date(System.currentTimeMillis());
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(msgTime);

        String timeFormat = "M月d日";
        String yearTimeFormat = "yyyy年";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar
                .get(Calendar.YEAR);
        if (yearTemp) {

            switch (getGapCount(date_before, date_now)) {
                case 0:
                    result = getHourAndMin(msgTime);
                    break;
                case 1:
                    result = "昨天 ";
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1];
                    break;
                default:
                    result = getTime(msgTime, timeFormat);
                    break;
            }
        } else {
            result = getYearTime(msgTime, yearTimeFormat);
        }
        return result;
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
                .getTime()) / (1000 * 60 * 60 * 24));
    }


    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
}
