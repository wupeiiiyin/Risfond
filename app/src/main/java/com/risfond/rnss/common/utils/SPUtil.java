package com.risfond.rnss.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/3/28.
 */

public class SPUtil {

    /**
     * 储存登录信息
     *
     * @param context
     */
    public static void saveLoginMsg(Context context, String account, String pwd, Login login) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account", account);
        editor.putString("pwd", pwd);
        editor.putString("Token", login.getToken());
        editor.putString("CompanyName", login.getCompanyName());
        editor.putString("EaseMobAccount", login.getEaseMobAccount());
        editor.putString("EaseMobPwd", login.getEaseMobPwd());
        editor.putString("Name", login.getName());
        editor.putString("headphoto", login.getHeadPhoto());
        editor.putInt("CompanyId", login.getCompanyId());
        editor.putInt("Id", login.getId());
        editor.putString("CharId", login.getCharId());
        editor.putString("TelNumber", login.getTelNumber());
        editor.putString("MobileNumber", login.getMobileNumber());
        editor.putString("EName", login.getEName());
        editor.commit();//只有调用commit方法才会把传回来的参数保存在文件上
    }

    /**
     * 储存免打扰
     *
     * @param context
     */
    public static void saveCloseMsg(Context context, boolean close) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("close", close);
        editor.commit();//只有调用commit方法才会把传回来的参数保存在文件上
    }

    /**
     * 获取免打扰状态
     *
     * @param context
     */
    public static boolean loadCloseMsg(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("close", false);
    }

    /**
     * 获取账户
     *
     * @param context
     */
    public static String loadAccount(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("account", "");
    }

    /**
     * 获取账户id
     *
     * @param context
     */
    public static int loadId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("Id", -1);
    }

    /**
     * 获取账户昵称
     *
     * @param context
     */
    public static String loadName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Name", "");
    }

    /**
     * 获取英文名字
     * @param context
     * @return
     */
    public static String loadEnglishName(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("EName","");
    }

    /**
     * 获取账户头像
     *
     * @param context
     */
    public static String loadHeadPhoto(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("headphoto", "");
    }

    /**
     * 获取账户座机号
     *
     * @param context
     */
    public static String loadTelNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("TelNumber", "");
    }

    /**
     * 获取账户手机号
     *
     * @param context
     */
    public static String loadMobileNumber(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("MobileNumber", "");
    }


    /**
     * 获取登录的IM账户
     *
     * @param context
     */
    public static String loadEaseMobAccount(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("EaseMobAccount", "");
    }

    /**
     * 获取账户所属公司
     *
     * @param context
     */
    public static String loadCompanyName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("CompanyName", "");
    }

    /**
     * 获取账户所属公司ID
     *
     * @param context
     */
    public static int loadCompanyId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("CompanyId", -1);
    }

    /**
     * 获取所在全国群组 ID
     *
     * @param context
     */
    public static String loadCompanyGroupId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("CharId", "");
    }

    /**
     * 清空登录信息
     *
     * @param context
     */
    public static void clearIMLoginData(Context context) {
        saveLoginMsg(context, SPUtil.loadAccount(context), "", new Login());
    }

    /**
     * 获取token
     *
     * @param context
     */
    public static String loadToken(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Token", "");
    }

    /**
     * 用数组形式存储简历搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveSearchHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("search_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("search_history_" + i);
            mEdit.putString("search_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出储搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadSearchHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("search_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("search_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储客户搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveCustomerHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("customer_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("customer_history_" + i);
            mEdit.putString("customer_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出储搜客户索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadCustomerHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("customer_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("customer_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储职位搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean savePositionHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("position_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("position_history_" + i);
            mEdit.putString("position_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出储搜职位索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadPositionHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("position_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("position_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储联系人搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveConstactsHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("constacts_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("constacts_history_" + i);
            mEdit.putString("constacts_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出储搜联系人索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadContactsHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("constacts_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("constacts_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储公共客户搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean savePublicCustomerHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("public_customer_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("public_customer_history_" + i);
            mEdit.putString("public_customer_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出公共客户搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadPublicCustomerHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("public_customer_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("public_customer_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储(推荐管理、发票管理)搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveReferenceHistoryArray(Context context, List<String> list, String type) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt(type + "size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove(type + i);
            mEdit.putString(type + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出(推荐管理、发票管理)搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadReferenceHistoryArray(Context context, String type) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt(type + "size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString(type + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储课程培训搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveMyCourseHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("my_course_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("my_course_history_" + i);
            mEdit.putString("my_course_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出课程培训搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadMyCourseHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("my_course_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("my_course_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储职位推荐搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveRecommendHistoryArray(Context context, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt("recommend_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove("recommend_history_" + i);
            mEdit.putString("recommend_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出职位推荐索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadRecommendHistoryArray(Context context) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt("recommend_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString("recommend_history_" + i, null));

        }
        return strs;
    }

    /**
     * 用数组形式存储搜索历史
     *
     * @param context
     * @param list
     * @return
     */
    public static boolean saveHistoryArray(Context context, String tag, List<String> list) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit = sp.edit();
        mEdit.putInt(tag + "_history_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit.remove(tag + "_history_" + i);
            mEdit.putString(tag + "_history_" + i, list.get(i));
        }
        return mEdit.commit();
    }

    /**
     * 取出搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> loadHistoryArray(Context context, String tag) {
        List<String> strs = new ArrayList<>();
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference.getInt(tag + "_history_size", 0);
        for (int i = 0; i < size; i++) {
            strs.add(mSharedPreference.getString(tag + "_history_" + i, null));

        }
        return strs;
    }

    /**
     * 保存用户的个人签名
     *
     * @param context
     * @param signature
     * @return
     */
    public static boolean saveUserSignature(Context context, String signature) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_signature", signature);
        return editor.commit();
    }

    /**
     * 获取保存的个人签名
     *
     * @param context
     * @return
     */
    public static String loadUserSignature(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String string = sp.getString("user_signature", "让每一个为梦想奋斗的人,更幸福的生活!");
        return string;
    }

}
