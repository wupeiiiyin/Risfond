package com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendanceDataUtils {

    /**
     * 获取系统当前日期
     */
    public static String getCurrDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取前/后 几个月的一个日期  切换月的时候用
     *
     * @param currentData
     * @param monthNum
     * @return
     */
    public static String getSomeMonthDay(String currentData, int monthNum) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy年MM月").parse(currentData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + monthNum);
        Date day = c.getTime();
        return new SimpleDateFormat("yyyy年MM月").format(day);
    }

    /**
     * "yyyy年MM月"--》yyyy-MM
     * @param currentData
     * @param
     * @return
     */
    public static String getSomeMonthDay2(String currentData) {
        String s= currentData;
        if(s.contains("年")){
            s = s.replace("年","-");
            s = s.replace("月","");
        }
        return s;
    }

    /**
     * "yyyy年MM月"--》yyyyMM
     * @param currentData
     * @param
     * @return
     */
    public static int getSomeMonthDay2Int(String currentData) {
        String s= currentData;
        int num = 0;
        if(s.contains("年")){
            s = s.replace("年","");
            s = s.replace("月","");
            try {
                num  = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

}
