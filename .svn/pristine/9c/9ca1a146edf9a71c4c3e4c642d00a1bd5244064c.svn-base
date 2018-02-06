package com.risfond.rnss.group.modleImpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.AddGroupSearchResponse;
import com.risfond.rnss.entry.SearchResponse;
import com.risfond.rnss.group.modleInterface.IAddGroupSearch;
import com.risfond.rnss.search.modleInterface.ISearch;

import java.util.Map;

/**
 * Created by Abbott on 2017/5/19.
 */

public class AddGroupSearchImpl implements IAddGroupSearch {
    private AddGroupSearchResponse response;

    @Override
    public void searchRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, AddGroupSearchResponse.class);
                    if (response != null) {
                        if (response.isStatus()) {//请求成功
                            callBack.onSuccess(response);
                        } else {
                            callBack.onFailed(response.getMessage());
                        }
                    } else {
                        callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }
            }

            @Override
            public void onFailed(Throwable ex) {
                if (ex.getMessage().equals("Canceled") || ex.getMessage().equals("Socket closed")) {
                    callBack.onFailed(ex.getMessage());
                } else {
                    callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));

                }
            }
        });
    }
}
