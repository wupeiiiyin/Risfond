package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/18.
 */

public class DepartListResponse {
    private int Code;
    private boolean Status;
    private String Message;
    private List<DepartList> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public boolean getStatus() {
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

    public List<DepartList> getData() {
        return Data;
    }

    public void setData(List<DepartList> data) {
        Data = data;
    }
}
