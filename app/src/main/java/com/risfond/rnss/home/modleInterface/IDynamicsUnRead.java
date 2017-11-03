package com.risfond.rnss.home.modleInterface;

import com.risfond.rnss.home.callback.ColleagueCallback;
import com.risfond.rnss.home.callback.DynamicsUnReadCallback;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 新同事接口
 */

public interface IDynamicsUnRead {
    /**
     *
     * @param callBack
     */
    void dynamicsUnReadRequest(String token, Map<String, String> request, String url, DynamicsUnReadCallback callBack);
}
