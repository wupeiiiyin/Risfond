package com.risfond.rnss.home.commonFuctions.myCourse.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * 课程培训
 * Created by Abbott on 2017/7/26.
 */

public interface IMyCourse {
    /**
     * 课程培训请求
     *
     * @param callBack
     */
    void positionSearchRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
