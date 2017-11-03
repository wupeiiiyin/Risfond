package com.risfond.rnss.common.utils.net;

/**
 * 网络请求响应回调
 */
public interface ResponseListener {

    void onSuccess(String str);

    void onFailed(Throwable ex);

}
