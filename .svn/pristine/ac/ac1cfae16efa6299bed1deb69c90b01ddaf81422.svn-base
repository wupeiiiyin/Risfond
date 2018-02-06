package com.risfond.rnss.common.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Abbott on 2017/9/28.
 */

public class IdentityUtils {
    //    位权值数组
    private static byte[] Wi = new byte[17];
    //    身份证前部分字符数
    private static final byte fPart = 6;
    //    身份证算法求模关键值
    private static final byte fMod = 11;
    //    旧身份证长度
    private static final byte oldIDLen = 99;//旧身份证15位，暂不考虑改为99
    //    新身份证长度
    private static final byte newIDLen = 18;
    //    新身份证年份标志
    private static final String yearFlag = "19";
    //    校验码串
    private static final String CheckCode = "10X98765432";
    //    最小的行政区划码
    private static final int minCode = 150000;
    //    最大的行政区划码
    private static final int maxCode = 700000;
    //    旧身份证号码
    //    private String oldIDCard="";
    //    新身份证号码
    //    private String newIDCard="";
    //    地区及编码
    //private String Area[][2] =

    //    最大的行政区划码
    private static String birthday;


    private static void setWiBuffer() {
        for (int i = 0; i < Wi.length; i++) {
            int k = (int) Math.pow(2, (Wi.length - i));
            Wi[i] = (byte) (k % fMod);
        }
    }

    //获取新身份证的最后一位:检验位
    private static String getCheckFlag(String idCard) {
        int sum = 0;
        //进行加权求和
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(idCard.substring(i, i + 1)) * Wi[i];
        }
        //取模运算，得到模值
        byte iCode = (byte) (sum % fMod);
        return CheckCode.substring(iCode, iCode + 1);
    }

    //判断串长度的合法性
    private static boolean checkLength(final String idCard, boolean newIDFlag) {
        boolean right = (idCard.length() == oldIDLen) || (idCard.length() == newIDLen);
        newIDFlag = false;
        if (right) {
            newIDFlag = (idCard.length() == newIDLen);
        }
        return right;
    }

    //获取时间串
    private static String getIDDate(final String idCard, boolean newIDFlag) {
        String dateStr = "";
        if (newIDFlag)
            dateStr = idCard.substring(fPart, fPart + 8);
        else
            dateStr = yearFlag + idCard.substring(fPart, fPart + 6);
        return dateStr;
    }

    //判断时间合法性
    private static boolean checkDate(final String dateSource) {
        String dateStr = dateSource.substring(0, 4) + "-" + dateSource.substring(4, 6) + "-" + dateSource.substring(6, 8);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            return (date != null);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //旧身份证转换成新身份证号码
    public static String getNewIDCard(final String oldIDCard) {
        //初始化方法
        IdentityUtils.setWiBuffer();
        if (!checkIDCard(oldIDCard)) {
            return oldIDCard;
        }
        String newIDCard = oldIDCard.substring(0, fPart);
        newIDCard += yearFlag;
        newIDCard += oldIDCard.substring(fPart, oldIDCard.length());
        String ch = getCheckFlag(newIDCard);
        newIDCard += ch;
        return newIDCard;
    }

    //新身份证转换成旧身份证号码
    public static String getOldIDCard(final String newIDCard) {
        //初始化方法
        IdentityUtils.setWiBuffer();
        if (!checkIDCard(newIDCard)) {
            return newIDCard;
        }
        String oldIDCard = newIDCard.substring(0, fPart) +
                newIDCard.substring(fPart + yearFlag.length(), newIDCard.length() - 1);
        return oldIDCard;
    }

    //判断身份证号码的合法性
    public static boolean checkIDCard(final String idCard) {
        //初始化方法
        IdentityUtils.setWiBuffer();

        int length = idCard.length();
        boolean isNew;
        if (length == oldIDLen)
            isNew = false;
        else if (length == newIDLen)
            isNew = true;
        else
            return false;

        //String message = "";
        if (!checkLength(idCard, isNew)) {
            //message = "ID长度异常";
            return false;
        }
        String idDate = getIDDate(idCard, isNew);
        if (!checkDate(idDate)) {
            //message = "ID时间异常";
            return false;
        }
        if (isNew) {
            String checkFlag = getCheckFlag(idCard);
            String theFlag = idCard.substring(idCard.length() - 1, idCard.length());
            if (!checkFlag.equals(theFlag)) {
                //message = "新身份证校验位异常";
                return false;
            }
        }
        return true;
    }

    //获取一个随机的"伪"身份证号码
    public static String getRandomIDCard(final boolean idNewID) {
        //初始化方法
        IdentityUtils.setWiBuffer();
        Random ran = new Random();
        String idCard = getAddressCode(ran) + getRandomDate(ran, idNewID) + getIDOrder(ran);
        if (idNewID) {
            String ch = getCheckFlag(idCard);
            idCard += ch;
        }
        return idCard;
    }

    //产生随机的地区编码
    private static String getAddressCode(Random ran) {
        if (ran == null) {
            return "";
        } else {
            int addrCode = minCode + ran.nextInt(maxCode - minCode);
            return Integer.toString(addrCode);
        }
    }

    //产生随机的出生日期
    private static String getRandomDate(Random ran, boolean idNewID) {
        // TODO Auto-generated method stub
        if (ran == null) {
            return "";
        }
        int year = 0;
        if (idNewID) {
            year = 1900 + ran.nextInt(2017 - 1900);
        } else {
            year = 1 + ran.nextInt(99);
        }
        int month = 1 + ran.nextInt(12);
        int day = 0;
        if (month == 2) {
            day = 1 + ran.nextInt(28);
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            day = 1 + ran.nextInt(31);
        } else {
            day = 1 + ran.nextInt(30);
        }
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        String dateStr = Integer.toString(year) + nf.format(month) + nf.format(day);
        return dateStr;
    }

    //产生随机的序列号
    private static String getIDOrder(Random ran) {
        // TODO Auto-generated method stub
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);
        if (ran == null) {
            return "";
        } else {
            int order = 1 + ran.nextInt(999);
            return nf.format(order);
        }
    }

    public IdentityUtils() {
        setWiBuffer();
    }

    private static String[][] constellations = {{"摩羯座", "水瓶座"}, {"水瓶座", "双鱼座"}, {"双鱼座", "白羊座"}, {"白羊座", "金牛座"}, {"金牛座", "双子座"}, {"双子座", "巨蟹座"}, {"巨蟹座", "狮子座"},
            {"狮子座", "处女座"}, {"处女座", "天秤座"}, {"天秤座", "天蝎座"}, {"天蝎座", "射手座"}, {"射手座", "摩羯座"}};
    //星座分割时间
    private static int[] date = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};

    /**
     * 星座生成 传进是日期格式为: yyyy-mm-dd
     *
     * @param time
     */
    public static String getConstellations(String time) {
        String[] data = time.split("-");
        int day = date[Integer.parseInt(data[1]) - 1];
        String[] cl1 = constellations[Integer.parseInt(data[1]) - 1];
        if (Integer.parseInt(data[2]) >= day) {
            return cl1[1];
        } else {
            return cl1[0];
        }
    }

    /**
     * 获取生日
     *
     * @param idCard
     * @return
     */
    public static String getBirthday(String idCard) {
        String dateSource = idCard.substring(fPart, fPart + 8);
        String dateStr = dateSource.substring(0, 4) + "-" + dateSource.substring(4, 6) + "-" + dateSource.substring(6, 8);
        return dateStr;
    }

    /**
     * 获取性别
     *
     * @param idCard
     * @return
     */
    public static String getSex(String idCard) {
        int number = Integer.parseInt(idCard.substring(16, 17));
        if (number % 2 == 0) {
            return "女";
        } else {
            return "男";
        }

    }

}
