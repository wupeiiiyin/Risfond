package com.risfond.rnss.home.resume.modleImpl;

import android.content.DialogInterface;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.CustomDialog;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ResumeSearchAddResponse;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.home.resume.activity.ResumeSearchResultActivity;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class ResumeSearchAddImpl implements IResumeSearch {
    ResumeSearchAddResponse response;

    @Override
    public void resumeRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, ResumeSearchAddResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onSuccess(response);
                        } else {
                            callBack.onFailed(response.getMessage());
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
