package com.risfond.rnss.mine.modleInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 个人信息接口
 */

public interface IUserInfo {
    /**
     * 登录请求
     *
     * @param request
     * @param callBack
     */
    void userInfoRequest(Map<String, String> request, String token, String url, ResponseCallBack callBack);
}
