package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ColleagueResponse;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.home.callback.ColleagueCallback;
import com.risfond.rnss.home.callback.DynamicsUnReadCallback;
import com.risfond.rnss.home.modleInterface.IColleague;
import com.risfond.rnss.home.modleInterface.IDynamicsUnRead;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class DynamicsUnReadImpl implements IDynamicsUnRead {
    DynamicsResponse response;

    @Override
    public void dynamicsUnReadRequest(String token, Map<String, String> request, String url, final DynamicsUnReadCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, DynamicsResponse.class);
                    if (response != null) {
                        if (response.isSuccess()) {//请求成功
                            callBack.onUnReadSuccess(response);
                        } else {
                            callBack.onUnReadFailed(response.getMessage()+"");
                        }
                    } else {
                        callBack.onUnReadFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onUnReadFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onUnReadError(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
