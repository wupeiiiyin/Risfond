package com.risfond.rnss.home.position.modelImpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.CustomerSearchResponse;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class PositionSearchImpl implements IPositionSearch {
    PositionSearchResponse response;

    @Override
    public void positionSearchRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, PositionSearchResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onSuccess(response);
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode(response.getMessage()));
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
