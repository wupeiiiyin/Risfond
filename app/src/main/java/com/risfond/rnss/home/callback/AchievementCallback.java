package com.risfond.rnss.home.callback;

/**
 * 绩效完成率
 * Created by Abbott on 2018/2/5.
 */

public interface AchievementCallback {

    void onAchievementSuccess(Object obj);

    void onAchievementFailed(String str);

    void onAchievementError(String str);
}
