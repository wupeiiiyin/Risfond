package com.risfond.rnss.home.position.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 客户接口
 */

public interface IRecommendList {
    /**
     * 推荐列表请求
     *
     * @param callBack
     */
    void recommendListRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
