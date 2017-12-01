package com.risfond.rnss.common.utils.net;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.risfond.rnss.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by idea on 2015-11-12.
 */
public class UtilHelper {

    private static String TAG = "UtilHelper";
    private static long modifyTime;

    public static void OutLog(String title, String message) {
        if (BuildConfig.DEBUG) {
            int len = 500;
            if (message == null) {
                return;
            }
            if (message.length() <= len) {
                Log.v(title, message);
            } else {
                //Log.v(title,"\n\n字符长度："+message.length());
                Log.v(title, "============================================================================================================ START LOG ============================================================================================================");
                for (int i = 0; i <= message.length() / len; i++) {
                    if (i < message.length() / len) {
                        Log.v(title, message.substring(len * i, len * (i + 1)));
                    } else if (len * i < message.length()) {
                        Log.v(title, message.substring(len * i, message.length()));
                    }
                }
                Log.v(title, "============================================================================================================ END PUT LOG ============================================================================================================");
            }
        }
    }

    public static void outLog(String title, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(title, message);
        }
    }

    //金额验证
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    public static String getCardGrade(String cardgrade) {
        if (cardgrade.equals("0")) {
            return "普卡";
        } else if (cardgrade.equals("1")) {
            return "金卡";
        } else if (cardgrade.equals("2")) {
            return "白金卡";
        } else if (cardgrade.equals("3")) {
            return "黑金卡";
        } else {
            return "普卡";
        }
    }

    public static String getBankCardtype(String cardgrade) {
        if (cardgrade.equals("2")) {
            return "借记卡";
        } else if (cardgrade.equals("3")) {
            return "信用卡";
        } else {
            return "未知类型";
        }
    }

    public static CharSequence getQuickPay(boolean status) {
        if (status) {
            SpannableStringBuilder builder = new SpannableStringBuilder("快捷支付");
            return builder;
        } else {
            return "";
        }
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isLenNumber(String str, int len) {
        if (isNullOrEmpty(str)) {
            return false;
        } else {
            if (str.length() == len) {
                Pattern p = Pattern.compile("^[0,9]$");
                Matcher m = p.matcher(str);
                return m.matches();
            } else {
                return false;
            }
        }
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isBankCard(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("[0-9]{15,19}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    public static boolean isCardNumber(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("[0-9]{16}$"); // 舌尖卡号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    public static String GetPhoneNumber(String phoneNumber) {
        if (phoneNumber.equals(null)) {
            return "";
        }
        if (phoneNumber.length() >= 11) {
            return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
        } else {
            return phoneNumber;
        }
    }

    public static String FormatDouble(double double1) {
        if (double1 == 0)
            return "0.00";
        Locale.setDefault(Locale.CHINA);
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        return decimalFormat.format(double1);
    }


    public static String FormatDouble(int amount) {
        NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.CHINA);
        System.out.println(usFormat.format(amount));
        NumberFormat germanFormat = NumberFormat
                .getIntegerInstance(Locale.CHINA);
        return germanFormat.format(amount);
    }


    public static String getFieldValue(String fieldName, Object model) {

        Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) {     //遍历所有属性
            if (fieldName.equals(field[j].getName())) {
                String name = field[j].getName();    //获取属性的名字
                //System.out.println("attribute name:"+name);

                name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString();    //获取属性的类型
                // System.out.println("type name:"+type);
                if (type.equals("class java.lang.String")) {   //如果type是类类型，则前面包含"class "，后面跟类名
                    try {
                        Method m = model.getClass().getMethod("get" + name);
                        String value = (String) m.invoke(model);    //调用getter方法获取属性值
                        //System.out.println("attribute value:"+value);
                        return value;
                    } catch (Exception ex) {
                        UtilHelper.OutLog(TAG, "没找到方法get" + name);
                        return "";
                    }
                }
            }
        }
        return "";
    }


    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    //4位间隔卡号
    public static String splitCode(String str) {
        String outStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                outStr += " " + String.valueOf(str.charAt(i));
            } else {
                outStr += String.valueOf(str.charAt(i));
            }
        }
        return outStr;
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

}
