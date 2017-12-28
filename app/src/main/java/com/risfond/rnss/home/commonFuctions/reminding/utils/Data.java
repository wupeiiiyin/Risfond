package com.risfond.rnss.home.commonFuctions.reminding.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Data implements Serializable{
    private String msg;
    private String time;

    public Data(String msg, String time) {
        this.msg = msg;
        this.time = time;
    }

    public Data() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
