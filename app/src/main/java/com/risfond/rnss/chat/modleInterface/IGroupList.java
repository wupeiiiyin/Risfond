package com.risfond.rnss.chat.modleInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 群组列表接口
 */

public interface IGroupList {
    /**
     * 获取群组列表请求
     *
     * @param request
     * @param callBack
     */
    void groupListRequest(Map<String, String> request, String token, String url, ResponseCallBack callBack);
}
