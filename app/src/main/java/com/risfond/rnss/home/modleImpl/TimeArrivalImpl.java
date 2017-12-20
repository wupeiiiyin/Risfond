package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.SearchResponse;
import com.risfond.rnss.entry.TimeArrivalResponse;
import com.risfond.rnss.home.callback.ITimeArrivalCallback;
import com.risfond.rnss.home.modleInterface.ITimeArrival;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class TimeArrivalImpl implements ITimeArrival {
    TimeArrivalResponse response;

    @Override
    public void timeArrivalRequest(String token, Map<String, String> request, String url, final ITimeArrivalCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, TimeArrivalResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onTimeArrivalSuccess(response.getData());
                        } else {
                            callBack.onTimeArrivalFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(response.getCode())));
                        }
                    } else {
                        callBack.onTimeArrivalFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onTimeArrivalFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onTimeArrivalFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
