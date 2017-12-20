package com.risfond.rnss.scan;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.callback.ScanCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 搜索接口
 */

public interface IScan {
    /**
     * 扫码请求
     *
     * @param callBack
     */
    void scanRequest(String token, Map<String, String> request, String url, ScanCallBack callBack);
}
