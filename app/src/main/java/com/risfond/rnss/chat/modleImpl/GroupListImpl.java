package com.risfond.rnss.chat.modleImpl;


import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.modleInterface.IGroupList;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.GroupListResponse;
import com.risfond.rnss.entry.UserInfoResponse;
import com.risfond.rnss.mine.modleInterface.IUserInfo;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 登录接口实现
 */

public class GroupListImpl implements IGroupList {
    private GroupListResponse response;

    @Override
    public void groupListRequest(Map<String, String> request, String token, String url, final ResponseCallBack callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, GroupListResponse.class);
                    if (response != null) {
                        if (response.getStatus()) {//请求成功
                            callBack.onSuccess(response.getData());
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(response.getCode())));
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
                callBack.onError(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
