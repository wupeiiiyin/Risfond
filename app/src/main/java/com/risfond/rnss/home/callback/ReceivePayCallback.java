package com.risfond.rnss.home.callback;

/**
 * 回款数据
 * Created by Abbott on 2018/2/5.
 */

public interface ReceivePayCallback {

    void onReceivePaySuccess(Object obj);

    void onReceivePayFailed(String str);

    void onReceivePayError(String str);
}
