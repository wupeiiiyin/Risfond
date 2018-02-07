package com.risfond.rnss.entry;

import java.util.List;

/**
 * Created by Abbott on 2017/5/18.
 */

public class WorkOrderResponse {

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

    private WorkOrder Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

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

    public WorkOrder getData() {
        return Data;
    }

    public void setData(WorkOrder data) {
        Data = data;
    }
}
