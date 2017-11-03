package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/10/23.
 */

public class WorkOrderReceiveResponse {

    /**
     * Code : 200
     * Status : true
     * Message : 请求成功
     */

    private int Code;
    private boolean Status;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
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

}
