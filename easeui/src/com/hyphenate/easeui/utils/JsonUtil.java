package com.hyphenate.easeui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * JSON转换工具类
 */
public class JsonUtil {

    public static String toJson(Object entity) {
        Gson gson = new Gson();
        return gson.toJson(entity);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, clazz);
    }

    public static boolean isJson(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }

    }

}
