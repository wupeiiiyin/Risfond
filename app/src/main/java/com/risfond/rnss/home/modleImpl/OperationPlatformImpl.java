package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.OperationPlatformResponse;
import com.risfond.rnss.entry.SearchResponse;
import com.risfond.rnss.home.callback.OperationPlatformCallback;
import com.risfond.rnss.home.modleInterface.IOperationPlatform;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class OperationPlatformImpl implements IOperationPlatform {
    OperationPlatformResponse response;

    @Override
    public void operationPlatformRequest(String token, Map<String, String> request, String url, final OperationPlatformCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, OperationPlatformResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onOperationPlatformSuccess(response.getData());
                        } else {
                            callBack.onOperationPlatformFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(response.getCode())));
                        }
                    } else {
                        callBack.onOperationPlatformFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onOperationPlatformFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onOperationPlatformFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
