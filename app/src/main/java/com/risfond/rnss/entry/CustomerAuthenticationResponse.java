package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/7/7.
 */

public class CustomerAuthenticationResponse {

    /**
     * Code : 0
     * Status : false
     * Message : 认证失败
     * Data : null
     */

    private int Code;
    private boolean Status;
    private String Message;
    private Object Data;

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

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }
}
