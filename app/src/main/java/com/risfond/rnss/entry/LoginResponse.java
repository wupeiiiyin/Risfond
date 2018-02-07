package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/4/12.
 */

public class LoginResponse {
    /**
     * 请求结果状态码
     */
    private int Code;
    /**
     * 请求成功/失败
     */
    private boolean Status;
    /**
     * 返回语义解释
     */
    private String Message;
    /**
     * 返回对象
     */
    private Login Data;

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

    public Login getData() {
        return Data;
    }

    public void setData(Login data) {
        Data = data;
    }
}
