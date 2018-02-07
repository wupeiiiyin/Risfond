package com.risfond.rnss.callback;

/**
 * Created by Abbott on 2017/4/13.
 * 给UI的回调
 */

public interface ScanCallBack {

    void onScanSuccess(Object obj);

    void onScanFailed(String str);

    void onScanError(String str);

}
