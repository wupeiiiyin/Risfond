package com.risfond.rnss.home.position.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 客户接口
 */

public interface IProcessNext {
    /**
     * 客户搜索请求
     *
     * @param callBack
     */
    void processNextRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
