package com.risfond.rnss.entry;

/**
 * Created by chh on 2017/7/26.
 */

public class ClientApplicationResponse {

    /**
     *
     * Status : true
     * Message : 请求成功
     */

    private boolean Status;
    private String Message;

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
}
