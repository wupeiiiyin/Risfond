package com.risfond.rnss.common.utils.net;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/7/26.
 * 接口请求
 */

public interface IHttpRequest {
    /**
     * @param callBack
     */
    void requestService(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
