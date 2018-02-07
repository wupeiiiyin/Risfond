package com.risfond.rnss.scan;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.callback.ScanCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.SearchResponse;
import com.risfond.rnss.search.modleInterface.ISearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/5/19.
 */

public class ScanImpl implements IScan {
    private SearchResponse response;

    @Override
    public void scanRequest(String token, Map<String, String> request, String url, final ScanCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    callBack.onScanSuccess(str);
                } else {
                    callBack.onScanFailed("time out");
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onScanFailed("time out");
            }
        });
    }
}
