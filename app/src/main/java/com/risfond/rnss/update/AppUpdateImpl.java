package com.risfond.rnss.update;


import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/20.
 * app更新检查接口的实现
 */

public class AppUpdateImpl implements IAppUpdate {
    private AppUpdateResponse response;

    @Override
    public void update(Map<String, String> request, String token, final ResponseCallBack callBack) {
        HttpUtil.getInstance().requestService(URLConstant.URL_APP_UPDATE, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, AppUpdateResponse.class);
                    if (response != null) {
                        if (response.getStatus()) {//请求成功
                            callBack.onSuccess(response.getData());
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(response.getCode())));
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
