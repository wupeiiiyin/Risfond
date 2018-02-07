package com.risfond.rnss.login.modleInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 登录接口
 */

public interface ILogin {
    /**
     * 登录请求
     *
     * @param request
     * @param callBack
     */
    void loginRequest(Map<String, String> request, String token, ResponseCallBack callBack);
}
