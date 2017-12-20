package com.risfond.rnss.home.resume.modleImpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.entry.ResumeSearchWholeResponse;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class ResumeSearchAllImpl implements IResumeSearch {
    ResumeSearchResponse wholeResponse;

    @Override
    public void resumeRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    wholeResponse = JsonUtil.fromJson(str, ResumeSearchResponse.class);
                    if (wholeResponse != null) {
                        if (wholeResponse.isStatus()) {//请求成功
                            callBack.onSuccess(wholeResponse);
                        } else {
                            callBack.onFailed(wholeResponse.getMessage());
                        }
                    } else {
                        callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
