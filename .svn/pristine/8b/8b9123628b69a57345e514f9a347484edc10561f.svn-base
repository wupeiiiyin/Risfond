package com.risfond.rnss.register.modelimpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.entry.RegisterCompanyResponse;
import com.risfond.rnss.register.modelinterface.IRegister;
import com.risfond.rnss.register.modelinterface.IRegisterCompany;

import java.util.Map;

/**
 * Created by Abbott on 2017/7/26.
 */

public class RegisterCompanyImpl implements IRegisterCompany {
    RegisterCompanyResponse response;

    @Override
    public void registerCompanyRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, RegisterCompanyResponse.class);
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
