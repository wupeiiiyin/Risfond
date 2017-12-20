package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/9/15.
 */

public class RegisterResponse {


    /**
     * Code : 0
     * Status : true
     * Message : 请求成功！
     * Data : 429097
     */

    private int Code;
    private boolean Status;
    private String Message;
    private String Data;

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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
