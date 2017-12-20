package com.risfond.rnss.group.modleInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 搜索接口
 */

public interface IAddGroupSearch {
    /**
     * 搜索请求
     *
     * @param callBack
     */
    void searchRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
