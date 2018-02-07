package com.risfond.rnss.common.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Abbott on 2017/6/27.
 */

public class UmengUtil {
    /**
     * 在Application中做的初始化
     */
    public static void initUmeng() {
        MobclickAgent.setDebugMode(true);//开启调试模式（如果不开启debug运行不会上传umeng统计）
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * 在BaseActivity跟BaseFragmentActivity中的onResume加入
     *
     * @param context
     */
    public static void onResumeToActivity(Context context) {
        MobclickAgent.onPageStart(context.getClass().getName());
        MobclickAgent.onResume(context);
    }

    /**
     * 在BaseActivity跟BaseFragmentActivity中的onPause加入
     *
     * @param context
     */
    public static void onPauseToActivity(Context context) {
        MobclickAgent.onPageEnd(context.getClass().getName());
        MobclickAgent.onPause(context);
    }

    /**
     * 在BaseFragment中的onResume加入
     *
     * @param context
     */
    public static void onResumeToFragment(Context context) {
        MobclickAgent.onPageStart(context.getClass().getName());
    }

    /**
     * 在BaseFragment中的onPause加入
     *
     * @param context
     */
    public static void onPauseToFragment(Context context) {
        MobclickAgent.onPageEnd(context.getClass().getName());
    }

    /**
     * 在登录成功的地方调用
     *
     * @param userId 用户id
     */
    public static void onLogin(String userId) {
        MobclickAgent.onProfileSignIn(userId);
    }

    /**
     * 在退出登录的地方调用
     */
    public static void onLogout() {
        MobclickAgent.onProfileSignOff();
    }
}
