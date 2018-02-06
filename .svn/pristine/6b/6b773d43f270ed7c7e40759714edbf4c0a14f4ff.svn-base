package com.risfond.rnss.home.customer.modelImpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.CustomerAuthenticationResponse;
import com.risfond.rnss.entry.CustomerSearchResponse;
import com.risfond.rnss.home.customer.modelInterface.ICustomerAuthentication;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/6/23.
 */

public class CustomerAuthenticationImpl implements ICustomerAuthentication {
    CustomerAuthenticationResponse response;

    @Override
    public void customerAuthenticationRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, CustomerAuthenticationResponse.class);
                    if (response != null) {
                        callBack.onSuccess(response);
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
