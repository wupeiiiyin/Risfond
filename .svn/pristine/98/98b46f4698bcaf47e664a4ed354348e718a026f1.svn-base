package com.risfond.rnss.contacts.modleImpl;


import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.contacts.modleInterface.ICompany;
import com.risfond.rnss.entry.CompanyListResponse;
import com.risfond.rnss.entry.DepartListResponse;
import com.risfond.rnss.entry.UserListResponse;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 登录接口实现
 */

public class CompanyImpl implements ICompany {
    private CompanyListResponse companyListResponse;
    private DepartListResponse departListResponse;
    private UserListResponse userListResponse;

    @Override
    public void companyRequest(final String type, String token, Map<String, String> request, String url, final ResponseCallBack callBack) {
        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    if (type.equals(Constant.LIST_COMPANY)) {
                        companyListResponse = JsonUtil.fromJson(str, CompanyListResponse.class);
                        if (companyListResponse != null) {
                            if (companyListResponse.getStatus()) {//请求成功
                                callBack.onSuccess(companyListResponse.getData());
                            } else {
                                callBack.onFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(companyListResponse.getCode())));
                            }
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                        }
                    } else if (type.equals(Constant.LIST_DEPART)) {
                        departListResponse = JsonUtil.fromJson(str, DepartListResponse.class);
                        if (departListResponse != null) {
                            if (departListResponse.getStatus()) {//请求成功
                                callBack.onSuccess(departListResponse.getData());
                            } else {
                                callBack.onFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(departListResponse.getCode())));
                            }
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                        }
                    } else {
                        userListResponse = JsonUtil.fromJson(str, UserListResponse.class);
                        if (userListResponse != null) {
                            if (userListResponse.getStatus()) {//请求成功
                                callBack.onSuccess(userListResponse.getData());
                            } else {
                                callBack.onFailed(PropertiesUtil.getMessageTextByCode(String.valueOf(userListResponse.getCode())));
                            }
                        } else {
                            callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                        }
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
