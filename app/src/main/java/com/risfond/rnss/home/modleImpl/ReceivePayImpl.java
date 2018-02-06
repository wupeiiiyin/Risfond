package com.risfond.rnss.home.modleImpl;

import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ReturnPayResponse;
import com.risfond.rnss.home.callback.ReceivePayCallback;
import com.risfond.rnss.home.modleInterface.IReceivePay;

import java.util.Map;

/**
 * Created by Abbott on 2018/2/5.
 */

public class ReceivePayImpl implements IReceivePay {

    ReturnPayResponse mResponse;

    @Override
    public void iReceivePayRequest(String token, Map<String, String> request, String url, final ReceivePayCallback callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)){
                    mResponse = JsonUtil.fromJson(str,ReturnPayResponse.class);
                    if (mResponse != null) {
                        if (mResponse.isStatus()) {
                            callBack.onReceivePaySuccess(mResponse);
                        } else {
                            callBack.onReceivePayFailed(PropertiesUtil.getMessageTextByCode("Error"));
                        }
                    } else {
                        callBack.onReceivePayFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onReceivePayFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onReceivePayFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
