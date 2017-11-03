package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by chh on 2017/7/26.
 */

public class ReferenceManagePageResponse {

    /**
     * Code : 0
     * Total : 1
     * Status : true
     * Message : 请求成功
     * Data :
     */

    private int Code;
    private int Total;
    private boolean Status;
    private String Message;
    private List<ReferenceItemInfo> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<ReferenceItemInfo> getData() {
        return Data;
    }

    public void setData(List<ReferenceItemInfo> data) {
        Data = data;
    }
}
