package com.risfond.rnss.home.modleInterface;

import com.risfond.rnss.home.callback.ColleagueCallback;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 新同事接口
 */

public interface IColleague {
    /**
     * 新同事请求
     *
     * @param callBack
     */
    void colleagueRequest(String token, Map<String, String> request, String url, ColleagueCallback callBack);
}
