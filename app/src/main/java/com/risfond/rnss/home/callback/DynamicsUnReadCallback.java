package com.risfond.rnss.home.callback;

/**
 * Created by Abbott on 2017/6/23.
 */

public interface DynamicsUnReadCallback {
    void onUnReadSuccess(Object obj);

    void onUnReadFailed(String str);

    void onUnReadError(String str);
}
