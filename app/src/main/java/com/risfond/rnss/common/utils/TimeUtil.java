package com.risfond.rnss.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Abbott on 2017/5/23.
 */

public class TimeUtil {
    private static SimpleDateFormat sf = null;

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 返回时间差
     */
    public static String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getTodayTime() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        return sf.format(d);
    }

    /**
     * 获取下一天的日期
     *
     * @return
     */
    public static String getNextDayTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return sf.format(c.getTime());
    }


    /**
     * 返回时间戳
     *
     * @param time
     * @return
     */
    public long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    public static String formatTime() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sf.format(d);
    }

    /**
     * 获取当前时间的年份
     *
     * @return 年份
     */
    public static int getYear() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前季度
     * @return
     */
    public static int getQuarter() {
        if (getMonth() <= 3) {
            return 1;
        } else if(getMonth()>3&&getMonth()<=6) {
            return 2;
        }else if(getMonth()>6&&getMonth()<=9) {
            return 3;
        } else if(getMonth()>9&&getMonth()<=12) {
            return 4;
        }
        return 1;
    }

    /**
     * 获取中午或者下午
     *
     * @return
     */
    public static String getAPM() {
        String apmStr = "上午";
        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        int apm = mCalendar.get(Calendar.AM_PM);
        if (apm == 0) {
            return apmStr;
        } else {
            apmStr = "下午";
            return apmStr;
        }
    }
}
