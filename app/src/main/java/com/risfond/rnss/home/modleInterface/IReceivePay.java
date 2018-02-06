package com.risfond.rnss.home.modleInterface;

import com.risfond.rnss.home.callback.ReceivePayCallback;

import java.util.Map;

/**
 * Created by Abbott on 2018/2/5.
 */

public interface IReceivePay {

    void iReceivePayRequest(String token, Map<String, String> request, String url, ReceivePayCallback callBack);
}
