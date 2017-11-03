package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/7/14.
 */

public class RecommendListResponse {
    private int Code;
    private boolean Status;
    private String Message;
    private List<RecommendList> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<RecommendList> getData() {
        return Data;
    }

    public void setData(List<RecommendList> data) {
        Data = data;
    }
}
