package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.AchievementResponse;
import com.risfond.rnss.home.callback.AchievementCallback;
import com.risfond.rnss.home.modleInterface.IAchievement;

import java.util.Map;

/**
 * 业绩完成率接口
 * Created by Abbott on 2018/2/5.
 */

public class AchievementImpl implements IAchievement {

    private AchievementResponse mResponse;

    @Override
    public void iAchievementRequest(String token, Map<String, String> request, String url, final AchievementCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    mResponse = JsonUtil.fromJson(str, AchievementResponse.class);
                    if (mResponse != null) {
                        if (mResponse.isStatus()) {
                            callBack.onAchievementSuccess(mResponse);
                        } else {
                            callBack.onAchievementFailed(mResponse.getMessage());
                        }
                    } else {
                        callBack.onAchievementFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onAchievementFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onAchievementFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
