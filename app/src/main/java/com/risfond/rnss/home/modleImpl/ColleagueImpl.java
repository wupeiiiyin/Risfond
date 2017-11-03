package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ColleagueResponse;
import com.risfond.rnss.entry.SearchResponse;
import com.risfond.rnss.home.callback.ColleagueCallback;
import com.risfond.rnss.home.modleInterface.IColleague;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class ColleagueImpl implements IColleague {
    ColleagueResponse response;

    @Override
    public void colleagueRequest(String token, Map<String, String> request, String url, final ColleagueCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, ColleagueResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onColleagueSuccess(response.getData());
                        } else {
                            callBack.onColleagueFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(response.getCode())));
                        }
                    } else {
                        callBack.onColleagueFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onColleagueFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onColleagueFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
