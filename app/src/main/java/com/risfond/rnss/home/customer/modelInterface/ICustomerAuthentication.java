package com.risfond.rnss.home.customer.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 客户认证
 */

public interface ICustomerAuthentication {
    /**
     * 客户客户认证请求
     *
     * @param callBack
     */
    void customerAuthenticationRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
