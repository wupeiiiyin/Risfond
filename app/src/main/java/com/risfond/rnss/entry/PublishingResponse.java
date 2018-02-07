package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/9/18.
 */

public class PublishingResponse {

    /**
     * Success : true
     * Message : 请求成功
     * StatusCode : 0
     */

    private boolean Success;
    private String Message;
    private int StatusCode;

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
