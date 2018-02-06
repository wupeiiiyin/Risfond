package com.risfond.rnss.home.modleInterface;

import com.risfond.rnss.home.callback.AchievementCallback;

import java.util.Map;

/**
 * 业绩完成率接口
 * Created by Abbott on 2018/2/5.
 */

public interface IAchievement {

    void iAchievementRequest(String token, Map<String, String> request, String url, AchievementCallback callBack);
}
