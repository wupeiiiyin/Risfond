package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/6/23.
 */

public class OperationPlatformResponse {

    /**
     * Code : 200
     * Status : true
     * Message : null
     * Data : {"ResumeCurrent":0,"HuikuanCurrent":0,"OfferCurrent":0,"QianyueCurrent":0,"RuzhiCurrent":0}
     */

    private int Code;
    private boolean Status;
    private Object Message;
    private OperationPlatform Data;

    public OperationPlatform getData() {
        return Data;
    }

    public void setData(OperationPlatform data) {
        Data = data;
    }

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

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }


}
