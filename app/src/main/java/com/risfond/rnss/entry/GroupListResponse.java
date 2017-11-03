package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/6/14.
 */

public class GroupListResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     * Data : {"AllId":"18088751857665","OtherId":"18805955821570"}
     */

    private int Code;
    private boolean Status;
    private String Message;
    private GroupList Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public boolean getStatus() {
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

    public GroupList getData() {
        return Data;
    }

    public void setData(GroupList data) {
        Data = data;
    }
}
