package com.risfond.rnss.common.utils;

import android.content.Context;


import com.risfond.rnss.application.MyApplication;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Android操作文件工具类
 */
public class PropertiesUtil {

    public static Properties getProperties(Context context) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(context.getAssets().open("code.properties"), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * @param code
     * @return
     */
    public static String getMessageTextByCode(String code) {
        if (MyApplication.properties.get("code" + code) != null) {
            return (String) MyApplication.properties.get("code" + code);
        } else {
            return (String) MyApplication.properties.get("code" + "Error");
        }
    }

}
