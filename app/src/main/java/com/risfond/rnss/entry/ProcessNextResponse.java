package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/9/1.
 */

public class ProcessNextResponse {

    /**
     * Data : 2266427
     * Success : true
     * Message : 新增评语成功！
     * StatusCode : 0
     */

    private int Data;
    private boolean Success;
    private String Message;
    private int StatusCode;

    public int getData() {
        return Data;
    }

    public void setData(int Data) {
        this.Data = Data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }
}
