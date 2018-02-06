package com.risfond.rnss.contacts.modleImpl;


import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.contacts.modleInterface.ICompany;
import com.risfond.rnss.contacts.modleInterface.IMyCustomer;
import com.risfond.rnss.entry.CompanyListResponse;
import com.risfond.rnss.entry.DepartListResponse;
import com.risfond.rnss.entry.MyCustomerResponse;
import com.risfond.rnss.entry.UserListResponse;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 通讯录客户接口实现
 */

public class MyCustomerImpl implements IMyCustomer {
    private MyCustomerResponse response;

    @Override
    public void request(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)){
                    response = JsonUtil.fromJson(str, MyCustomerResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onSuccess(response.getData());
                        } else {
                            callBack.onFailed(response.getMessage());
                        }
                    } else {
                        callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                }else {
                    callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onError(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
