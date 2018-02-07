package com.risfond.rnss.home.commonFuctions.workorder.modelimpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.IHttpRequest;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.WorkOrderReceiveResponse;

import java.util.Map;

/**
 * Created by chh on 2017/7/26.
 */

public class WorkOrderReceiveImpl implements IHttpRequest {
    WorkOrderReceiveResponse response;

    @Override
    public void requestService(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, WorkOrderReceiveResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onSuccess(response);
                        } else {
                            callBack.onFailed(response.getMessage()+"");
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