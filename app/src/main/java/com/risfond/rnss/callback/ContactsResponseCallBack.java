package com.risfond.rnss.callback;

import java.util.List;

/**
 * Created by Abbott on 2017/4/13.
 * 给UI的回调
 */

public interface ContactsResponseCallBack {

    void onSuccess(List<?> data, String type);

    void onFailed(String str);

    void onError(String str);

}
