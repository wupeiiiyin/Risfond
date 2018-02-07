package com.risfond.rnss.home.position.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 客户接口
 */

public interface IRecommendProcess {
    /**
     * 推荐流程请求
     *
     * @param callBack
     */
    void recommendProcessRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
