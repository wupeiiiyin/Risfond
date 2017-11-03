package com.risfond.rnss.register.modelinterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/7/26.
 * 注册第一步
 */

public interface IRegister {
    void registerRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
